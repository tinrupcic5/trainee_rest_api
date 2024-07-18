@file:Suppress("unused")

import com.bitroot.trainee.restapi.logging.AbstractStringId
import org.slf4j.MDC
import java.util.function.Function
import java.util.function.Supplier

/**
 * Utility class to access the logging context in a structured way.
 * An instance of this class can be used to capture the current state the logging context, so it can be "transported" to
 * another thread or the current logging context of the thread can be restored to this captured state.
 */
class LoggingContext private constructor(
    val context: Map<String, String>,
) {
    /**
     * This function runs the supplied code block with this logging context.
     * A typical use case is if we want to propagate the logging context to a new thread.
     */
    fun <T> applyTo(closure: Supplier<T>): T {
        val capturedLoggingContext = capture()
        // copy the logging context, so we don't unintentionally modify the version in this class
        MDC.setContextMap(LinkedHashMap(context))
        return try {
            closure.get()
        } finally {
            capturedLoggingContext.applyToThread()
        }
    }

    /**
     * Restore the global MDC to this logging context version.
     */
    fun applyToThread() {
        MDC.setContextMap(context)
    }

    /**
     * Well known keys for the logging context.
     */
    enum class Key {
        HTTP_REQUEST_METHOD,
        HTTP_REQUEST_URI,
        HTTP_REQUEST_ID,
        AUTHENTICATION,
        USE_CASE,
        APPLICATION_STAGE,
        ROLES,
        AUTHORITIES,
        WITH_SYSTEM_PRIVILEGES,
    }

    enum class UseCase {
        CHECK_FOR_REGISTRATION_EMAIL,
        DELETE_OLD_QR_CODE,
        MEMBERSHIP_CHECK,
        MEMBERSHIP_CHECK_FOR_UNPAID_FEE_OVER_MONTH_AND_TEN_DAYS
    }

    companion object {
        /**
         * This method is used to keep updates to the logging context done in the closure confined in the closure, so they
         * won't propagate up.
         */
        @JvmStatic
        fun <T> applyNew(closure: Supplier<T>): T {
            val capturedLoggingContext = capture()
            return try {
                closure.get()
            } finally {
                capturedLoggingContext.applyToThread()
            }
        }

        /**
         * This method is used to keep updates to the logging context done in the closure confined in the closure, so they
         * won't propagate up.
         */
        @JvmStatic
        fun applyNew(closure: Runnable) {
            applyNew<Int> {
                closure.run()
                1 // turn the lambda into a Supplier<Integer>. So this is not a recursive call.
            }
        }

        @JvmStatic
        fun put(
            key: Key,
            value: Any,
        ) {
            MDC.put(key.name.lowercase(), value.toString())
        }

        /**
         * Put an id into the logging context in a standardized way.
         */
        @JvmStatic
        fun putId(id: AbstractStringId) {
            MDC.put(id::class.simpleName, id.value)
        }

        /**
         * Put a collection of ids into the logging context in a standardized way. It is assumed that all Ids reference the
         * same entity.
         */
        @JvmStatic
        fun putIds(ids: Collection<AbstractStringId>) {
            // it is assumed that all ids reference the same type
            ids.firstOrNull()?.let { MDC.put(it::class.simpleName, ids.map(AbstractStringId::value).joinToString()) }
        }

        /**
         * Put a collection of objects with ids into the logging context in a standardized way. It is assumed that all Ids reference the
         * same entity.
         */
        @JvmStatic
        fun <T> putIdsFromCollection(
            domainObjects: Collection<T>,
            mapper: Function<T, AbstractStringId>,
        ) {
            // it is assumed that all ids reference the same type
            domainObjects.firstOrNull()?.let { firstDomainObject: T ->
                val typeName = mapper.apply(firstDomainObject).javaClass.simpleName
                MDC.put(typeName, domainObjects.joinToString { mapper.apply(it).value })
            }
        }

        @JvmStatic
        fun put(useCase: UseCase) {
            put(Key.USE_CASE, useCase.name)
        }

        /**
         * This method captures the logging context, so it can be used run code in another thread with the same context
         * information.
         *
         * `
         * var context = LoggingContext.capture()
         *
         *
         * ...
         * // in another tread or task
         * context.run(() -> {
         * // do something
         * })
         ` *
         */
        @JvmStatic
        fun capture(): LoggingContext {
            var copyOfContextMap = MDC.getCopyOfContextMap()
            if (copyOfContextMap == null) {
                copyOfContextMap = LinkedHashMap()
            }
            return LoggingContext(copyOfContextMap)
        }
    }
}

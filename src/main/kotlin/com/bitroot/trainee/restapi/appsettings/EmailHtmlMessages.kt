package com.bitroot.trainee.restapi.appsettings

import org.springframework.core.io.FileSystemResource

fun sendPaymentWarningMessageEn(
    name: String,
    schoolName: String,
    lastPayment: String,
    validUntil: String,
    forMonth: Int,
): String =
    """
            <html>
            <body>
            <p style="font-size: 15px;">Hi $name,</p>
            
            
            <p style="font-size: 15px;">We would like to remind you that your last month's training fee is overdue.</p> 
            <p style="font-size: 15px;">Please make the payment as soon as possible to avoid any disruption to your training.</p>

            <br><br>
            <p style="font-size: 15px;">Last payment: $lastPayment for $forMonth Month.</p>
            <p style="font-size: 15px;">Valid until: $validUntil</p>
            <br><br>
            
            <p style="font-size: 15px;">Thanks.</p>
            <p style="font-size: 15px;">Sincerely,</p>
            
            <p style="font-size: 15px;">$schoolName</p>
            
            ${makeSignature()}
       
            </body>
            </html>
    """.trimIndent()

fun sendPaymentWarningMessageHr(
    name: String,
    schoolName: String,
    lastPayment: String,
    validUntil: String,
    forMonth: Int,
): String =
    """
            <html>
            <body>
            <p style="font-size: 15px;">Bok $name,</p>
            
            
            <p style="font-size: 15px;">Željeli bismo te podsjetiti da je tvoja naknada za prošli mjesec kasnila.</p>
            <p style="font-size: 15px;">Molimo te da uplatu izvršiš što je prije.</p>
          
            <br><br>
            <p style="font-size: 15px;">Zadnja uplata: $lastPayment za $forMonth mjesec.</p>
            <p style="font-size: 15px;">Uplata vrijedila do: $validUntil</p>
            <br><br>
            
            <p style="font-size: 15px;">Hvala.</p>
            <p style="font-size: 15px;">Lijep pozdrav,</p>
            
            <p style="font-size: 15px;">$schoolName</p>
            
            ${makeSignature()}
            
            </body>
            </html>
    """.trimIndent()

fun sendTemporaryPasswordEmailMessageEn(name: String, password: String, schoolName: String): String =
    """
            <html>
            <body>
            <p style="font-size: 15px;">Hi $name,</p>
                
            <br>
       
            <p style="font-size: 15px;">Registration for Trainee app was successfull!</p>
       
            <br>
            
            <p style="font-size: 15px;">This is you temporary password: <b style="font-size: 18px;">$password</b></p>
            <p style="font-size: 15px;">If you want, you can reset your password at first login</p>
            
            <br><br>
            <p style="font-size: 15px;">$schoolName</p>
            
            
            ${makeSignature()}
            
            </body>
            </html>
    """.trimIndent()

fun sendTemporaryPasswordEmailMessageHr(name: String, password: String, schoolName: String): String =
    """
        <html>
        <body>
        <p style="font-size: 15px;">Bok $name,</p>
        
        <br>
       
        <p style="font-size: 15px;">Registracija za Trainee app je uspjela!</p>
       
        <br>
        
        <p style="font-size: 15px;">Tvoja privremena lozinka: <b style="font-size: 18px;">$password</b></p>
        <p style="font-size: 15px;">Ako želiš, možeš lozinku resetirati prilikom prijave</p>
        
        <br><br>
        
        <p style="font-size: 15px;">$schoolName</p>
        
        
         ${makeSignature()}

        </body>
        </html>
    """.trimIndent()

fun sendEmailMessageWithResetKey(name: String, key: String, expires: String, file: FileSystemResource): String = """
            <html>
            <body>
            <p style="font-size: 15px;">Hi $name,</p>

            <p style="font-size: 15px;">Reset Key: <b style="font-size: 18px;">$key</b></p>
            <p style="font-size: 15px;">This key expires: $expires</p>


            <br><br>

            ${makeSignature()}

            </body>
            </html>
""".trimIndent()

fun sendSuccessfullRegistrationEmailMessageHr(
    name: String,
    schoolName: String,
): String =
    """
            <html>
            <body>
            <p style="font-size: 15px;">Bok $name,</p>
            
            <p style="font-size: 15px;">Registracija za Trainee app je uspjela!</p>
           
            <p style="font-size: 15px;">Ovo je tvoj QR kôd za prijavu u dvoranu.</p>
            
            <img src="cid:image" alt="QRCode" />
            <br><br>
            <p style="font-size: 15px;">$schoolName</p>
            
            
            ${makeSignature()}
            
            </body>
            </html>
    """.trimIndent()

fun sendSuccessfullRegistrationEmailMessageEn(
    name: String,
    schoolName: String,
): String =
    """
            <html>
            <body>
            <p style="font-size: 15px;">Hi $name,</p>
                
            <p style="font-size: 15px;">Registration for Trainee app was successfull!</p>
       
            <p style="font-size: 15px;">This is your qr code for checking in into the gym.</p>
            
            <img src="cid:image" alt="QRCode" />
            <br><br>
            <p style="font-size: 15px;">$schoolName</p>
            
            
            ${makeSignature()}
            
            </body>
            </html>
    """.trimIndent()

fun makeSignature(): String =
    """<p style="font-family:'Monaco';font-size: 13px;">bitroot</p>
       <p style="font-family:'Monaco';font-size: 13px;">[ground studio]</p>
    """.trimMargin()

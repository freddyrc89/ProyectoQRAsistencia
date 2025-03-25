package com.freddy.proyectoqrasistencia

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set



fun generarQR(codigo: String,size: Int =512): Bitmap?{
        val writer = QRCodeWriter()
        return try {
            val bitMatrix: BitMatrix = writer.encode(codigo, BarcodeFormat.QR_CODE, size, size)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = createBitmap(width, height)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap[x, y] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
                }
            }
            bitmap

        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

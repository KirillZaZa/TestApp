package com.kizadev.myapplication.extensions



fun String.changeUrlPhotoSize(): String{
    return this.replace("100x100","600x600")
}
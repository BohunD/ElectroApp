package com.example.electroapp.data.util

import com.example.electroapp.R
import com.example.electroapp.data.models.Category

const val DATA_USERS = "users"
const val DATA_USER_EMAIL = "email"
const val DATA_USER_USERNAME = "username"
const val DATA_USER_IMAGE_URL = "imageUrl"
const val DATA_USER_FOLLOW = "followUsers"
const val DATA_IMAGES = "images"
const val DATA_AD_USER_ID = "userId"
const val DATA_AD_PHOTOS = "photos"

const val PARAM_USER_ID = "userId"
const val PARAM_USER_NAME = "userName"

const val DATA_ADS= "advertisements"


const val DEFAULT_USER_PHOTO = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/2048px-User-avatar.svg.png"
const val NO_ADVERTISEMENT_PHOTO = "https://t4.ftcdn.net/jpg/04/99/93/31/360_F_499933117_ZAUBfv3P1HEOsZDrnkbNCt4jc3AodArl.jpg"

val CATEGORIES = arrayListOf(
    Category("Smartphones", R.drawable.phones, mapOf(
        "Brand" to arrayListOf("Other", "Apple", "Samsung", "Huawei", "Xiaomi",
            "Meizu", "Lenovo", "Oppo", "Pixel", "Other"),

        "Memory (gb)" to arrayListOf("Other","8", "16", "32", "64", "128", "256", "512", "1024"),

        "Display size" to arrayListOf(
            "Other", "<= 4.59\"", "4.6\" - 5\"", "5.01\" - 5.5\"", "5.55\" - 6\"",
        "6.01\" - 6.49\"", ">= 6.5\""),

        "Battery capacity" to arrayListOf(
            "Other",
            "<= 2999 mAh",
            "3000 - 3999 mAh",
            "4000 - 4999 mAh",
            "5000 - 5999 mAh",
            ">= 6000 mAh",
            )
    )
    ),
    Category("Laptops", R.drawable.laptops, mapOf(
        "Brand" to arrayListOf(
            "Other",
            "Apple",
            "Asus",
            "Lenovo",
            "Acer",
            "Dell",
            "HP",
            "MSI",
            "Samsung",
            "Huawei",
            "Xiaomi",
            "Other"),
        "Processor" to arrayListOf(
            "Other",
            "Intel Core i9",
            "Intel Core i7",
            "Intel Core i5",
            "Intel Core i3",
            "AMD Ryzen 9",
            "AMD Ryzen 7",
            "AMD Ryzen 5",
            "AMD Ryzen 3",
            "Intel Pentium",
            "Intel Celeron",
            "AMD Athlon",
            "Intel Atom",
            "Intel Xeon",
        ),
        "Display diagonal" to arrayListOf(
            "Other",
            "9\"-12.5\"",
            "13\"",
            "14\"",
            "15\"-16\"",
            "17\"",
            "18\" and more"
        ),
        "RAM" to arrayListOf(
            "Other", "10 - 12 GB", "16 - 24 GB", "4 GB", "6 - 8 GB",
        ),
        "Drive type" to arrayListOf(
            "Other", "SSD", "HDD", "SSD + HDD", "eMMC"
        ),
        "Type of video card" to arrayListOf(
            "Other", "Discrete", "Integrated", "Discrete + Integrated"
        ),
        "Storage capacity" to arrayListOf(
            "Other",
            "64 - 120 GB",
            "128 - 250 GB",
            "256-500 GB",
            "512 - 1000 GB",
            "1 TB and more"
        )
    )
    ),
    Category("Watches", R.drawable.watches, mapOf(
        "Brand" to arrayListOf(""),
        "Compatible OS" to arrayListOf(""),
        "Diagonal display" to arrayListOf(""),
        "Built-in memory capacity" to arrayListOf(""),
        "Operating System" to arrayListOf(""),
        "Communication Type" to arrayListOf(""),
        "Shape" to arrayListOf(""),
        "Body material" to arrayListOf("")
    )),
    Category("Monitors", R.drawable.monitors, mapOf(

    )),
    Category("Keyboards", R.drawable.keyboards, mapOf(

    )),
    Category("Printers", R.drawable.printers, mapOf(

    )),
    Category("Cameras", R.drawable.cameras, mapOf(

    )),
    Category("Headphones", R.drawable.headphones, mapOf(

    )),
    Category("Powerbanks", R.drawable.powerbanks, mapOf(

    )),
)
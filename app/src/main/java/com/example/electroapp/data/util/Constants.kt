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
const val DATA_AD_ID = "adId"
const val DATA_USER_LIKES = "likedAds"
const val DATA_AD_LIKES = "likedUsers"
const val DATA_AD_NAME_IN_ARRAY = "nameInArray"

const val PARAM_USER_ID = "userId"
const val PARAM_USER_NAME = "userName"

const val DATA_ADS= "advertisements"


const val DEFAULT_USER_PHOTO = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/2048px-User-avatar.svg.png"
const val NO_ADVERTISEMENT_PHOTO = "https://t4.ftcdn.net/jpg/04/99/93/31/360_F_499933117_ZAUBfv3P1HEOsZDrnkbNCt4jc3AodArl.jpg"

val CATEGORIES = arrayListOf(
    Category("Smartphones", R.drawable.phones, mapOf(
        "Brand" to arrayListOf("Other", "Apple", "Samsung", "Huawei", "Xiaomi",
            "Meizu", "Lenovo", "Oppo", "Pixel"),

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
            "Other", "Discrete", "Integrated", "Discrete + Integrated", "Without video card"
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
        "Brand" to arrayListOf("Other", "Apple", "Samsung", "Huawei", "Xiaomi",
            "Meizu", "Lenovo", "Oppo", "Pixel"),
        "Case Material" to arrayListOf("Other", "Stainless Steel", "Titanium", "Plastic"),
        "Water Resistance" to arrayListOf("Other", "IPX5", "IPX7", "10 ATM", "20 ATM"),
        "Strap Material" to arrayListOf("Other", "Leather", "Metal", "Rubber"),
        "Features" to arrayListOf("Other", "Fitness Tracking", "GPS", "Smart Notifications"),
    ))
    ,
    Category("Monitors", R.drawable.monitors, mapOf(
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
            "LG",
            "Philips",
            "BenQ",
            ),
        "Aspect Ratio" to arrayListOf("Other", "16:9", "21:9", "32:9"),
        "Panel Type" to arrayListOf("Other", "IPS", "TN", "VA", "OLED"),
        "Color Gamut" to arrayListOf("Other", "sRGB", "Adobe RGB", "DCI-P3"),
        "Curved" to arrayListOf("Other", "Yes", "No"),
        "HDR Support" to arrayListOf("Other", "HDR10", "Dolby Vision", "HDR400"),
    )),
    Category("Keyboards", R.drawable.keyboards, mapOf(
        "Brand" to arrayListOf("Other",  "A4Tech",
            "Asus",
            "Gembird",
            "Genius",
            "Keychron",
            "Logitech",
            "Razer",
            "Vinga",
            ),
        "Key Rollover" to arrayListOf("Other", "6-Key", "N-Key"),
        "Macro Keys" to arrayListOf("Other", "Yes", "No"),
        "Wrist Rest" to arrayListOf("Other", "Detachable", "Integrated", "None"),
        "Keycap Material" to arrayListOf("Other", "ABS", "PBT"),
        "Layout Style" to arrayListOf("Other", "Compact", "Tenkeyless", "Full Size"),
    )),
    Category("Printers", R.drawable.printers, mapOf(
        "Brand" to arrayListOf("Other", "Brother",
            "Canon",
            "Epson",
            "Fujifilm",
            "HP",
            "Jetix",
            "Pantum",
            "Samsung",
            "Xerox"),
        "Color Printing" to arrayListOf("Other", "Yes", "No"),
        "Paper Size" to arrayListOf("Other", "Letter", "A4", "Legal", "A3"),
        "Duplex Printing" to arrayListOf("Other", "Yes", "No"),
        "Mobile Printing" to arrayListOf("Other", "AirPrint", "Google Cloud Print"),
        "Duty Cycle" to arrayListOf("Other", "Low Volume", "Medium Volume", "High Volume"),
    )),

    Category("Cameras", R.drawable.cameras, mapOf(
        "Brand" to arrayListOf("Other", "Canon  ",
            "Fuji  ",
            "Fujifilm  ",
            "Kodak  ",
            "Leica  ",
            "Nikon  ",
            "Panasonic  ",
            "Polaroid  ",
            "Sony  "),
        "Video Recording" to arrayListOf("Other", "Up to 1080p", "4K", "8K"),
        "Image Stabilization" to arrayListOf("Other", "Optical", "Digital", "None"),
        "Focus System" to arrayListOf("Other", "Phase Detection", "Contrast Detection", "Hybrid"),
        "Viewfinder Type" to arrayListOf("Other", "Optical", "Electronic", "None" ),
        "ISO Range" to arrayListOf("Other", "Up to 3200", "3201 - 6400", "6401 - 12800" ),
    ))
    ,
    Category("Headphones", R.drawable.headphones, mapOf(
        "Brand" to arrayListOf("Other", "Apple ",
            "HyperX ",
            "JBL ",
            "Logitech ",
            "Panasonic ",
            "Razer ",
            "Samsung ",
            "Sennheiser ",
            "Sony ",
            "Xiaomi"),
        "Microphone" to arrayListOf("Other", "Built-In", "Detachable", "No" ),
        "Wireless Type" to arrayListOf("Other", "Bluetooth", "RF", "Infrared" ),
        "Comfort Features" to arrayListOf("Other", "Memory Foam Earpads", "Adjustable Headband", "Swivel Cups" ),
        "Driver Size" to arrayListOf("Other", "40mm", "50mm", "Over 50mm" ),
        "Soundstage" to arrayListOf("Other", "Open-Back", "Closed-Back", "Semi-Open" ),
        // Add more attributes specific to headphones
    ))
    ,
    Category("Powerbanks", R.drawable.powerbanks, mapOf(
        "Brand" to arrayListOf("Other", "Baseus  ",
            "Blow  ",
            "Borofone  ",
            "EcoFlow  ",
            "Hoco  ",
            "Promate  ",
            "Remax  ",
            "Xiaomi  ",
            "Silicon Power ",
            "Без бренду"),
        "Output Ports" to arrayListOf("Other", "USB-A", "USB-C", "Micro-USB", "Lightning", "Other"),
        "Pass-Through Charging" to arrayListOf("Other", "Yes", "No", "Other"),
        "Charging Speed" to arrayListOf("Other", "Standard", "Fast Charging", "Quick Charge", "Other"),
        "Weight" to arrayListOf("Other", "Up to 200g", "201 - 400g", "401 - 600g", "Other"),
    ))
)
package com.example.classes

class WebNew(
    override var title: String,
    override var subtitle: String,
    override var publishDate: String,
    var id: Int,
    var video: String,
    var url: String,
    var bussiness: String
): New(title, subtitle, publishDate)
{

}
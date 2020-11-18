package com._112.asgard.heimdall.service

import com._112.asgard.heimdall.jpa.Image

interface ImageService{
    fun getImage(imageId: String): Image

}
package br.com.moraesit.service

import br.com.moraesit.domain.Review
import br.com.moraesit.util.CommonUtil.Companion.delay

class ReviewService {

    fun retriveReview(productId: String): Review {
        delay(1000)
        return Review(200, 4.5)
    }
}
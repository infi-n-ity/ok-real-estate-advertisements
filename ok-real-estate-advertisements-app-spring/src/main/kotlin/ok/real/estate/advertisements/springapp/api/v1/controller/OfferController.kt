package ok.real.estate.advertisements.springapp.api.v1.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ok.real.estate.advertisements.api.v1.models.AdOffersRequest
import ok.real.estate.advertisements.api.v1.models.AdOffersResponse
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.mappers.v1.fromTransport
import ok.real.estate.advertisements.mappers.v1.toTransportOffers

@RestController
@RequestMapping("v1/ad")
class OfferController(private val processor: MkplAdProcessor) {

    @PostMapping("offers")
    suspend fun searchOffers(@RequestBody request: AdOffersRequest): AdOffersResponse {
        val context = MkplContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportOffers()
    }
}

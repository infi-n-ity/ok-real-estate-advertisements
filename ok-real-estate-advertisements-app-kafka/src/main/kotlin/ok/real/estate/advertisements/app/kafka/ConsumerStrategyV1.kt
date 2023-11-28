package ok.real.estate.advertisements.app.kafka

import ok.real.estate.advertisements.api.v1.apiV1RequestDeserialize
import ok.real.estate.advertisements.api.v1.apiV1ResponseSerialize
import ok.real.estate.advertisements.api.v1.models.IRequest
import ok.real.estate.advertisements.api.v1.models.IResponse
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.mappers.v1.fromTransport
import ok.real.estate.advertisements.mappers.v1.toTransportAd

class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: MkplContext): String {
        val response: IResponse = source.toTransportAd()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: MkplContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}
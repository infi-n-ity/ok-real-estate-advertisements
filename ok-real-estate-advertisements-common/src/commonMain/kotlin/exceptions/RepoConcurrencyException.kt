package ok.real.estate.advertisements.common.exceptions

import ok.real.estate.advertisements.common.models.MkplAdLock

class RepoConcurrencyException(expectedLock: MkplAdLock, actualLock: MkplAdLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)

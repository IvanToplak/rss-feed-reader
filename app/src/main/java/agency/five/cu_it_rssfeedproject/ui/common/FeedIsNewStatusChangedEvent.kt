package agency.five.cu_it_rssfeedproject.ui.common

import io.reactivex.subjects.PublishSubject

class FeedIsNewStatusChangedEvent {

    private val subject = PublishSubject.create<Unit>()

    fun subscribe() = subject

    fun publish() = subject.onNext(Unit)
}
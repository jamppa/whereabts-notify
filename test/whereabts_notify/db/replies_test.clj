(ns whereabts-notify.db.replies-test
	(:use
		[whereabts-notify.db.replies]
		[whereabts-notify.db.replies-test-fixtures]
		[whereabts-notify.db.init]
		[midje.sweet]))

(background (before :facts (setup-test-db)))

(fact "should find reply by its id"
	(find-reply "509d513f61395f0ebbd5e39a") => reply-a)
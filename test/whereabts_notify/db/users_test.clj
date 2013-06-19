(ns whereabts-notify.db.users-test
	(:use
		[whereabts-notify.db.users]
		[whereabts-notify.db.users-test-fixtures]
		[whereabts-notify.db.init]
		[midje.sweet]))

(background (before :facts (setup-test-db)))

(fact "should find user with object id"
	(find-user "509d513f61395f0ebbd5e38a") => user-a)

(fact "should find gcm id of user"
	(find-user-gcm-id "509d513f61395f0ebbd5e38a") => "123AbC")
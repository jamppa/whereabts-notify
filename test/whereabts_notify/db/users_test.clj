(ns whereabts-notify.db.users-test
	(:use
		[whereabts-notify.db.users]
		[whereabts-notify.db.users-test-fixtures]
		[whereabts-notify.db.init]
		[midje.sweet]))

(background (before :facts (setup-test-db)))

(fact "should find user by object id"
	(find-user "509d513f61395f0ebbd5e38a") => user-a)

(fact "should find gcm id of user"
	(find-user-gcm-id "509d513f61395f0ebbd5e38a") => "123AbC")

(fact "should return nil when finding user that does not exist"
	(find-user "509d513f61395f0ebbd5e666") => nil)

(fact "should return nil when user doesn't have gcm id"
	(find-user-gcm-id "509d513f61395f0ebbd5e38b") => nil)

(fact "should return nil when finding gcm id of nonexisting user"
	(find-user-gcm-id "509d513f61395f0ebbd5e666") => nil)
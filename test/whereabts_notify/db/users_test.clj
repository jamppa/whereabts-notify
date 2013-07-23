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

(fact "should find userprofile by user id"
	(find-profile-by-user-id (:_id user-a)) => user-a-profile)

(fact "should not find userprofile by user id that doesnt exist"
	(find-profile-by-user-id (:_id user-b)) => nil)

(def obj {:user_id (:_id user-a)})
(fact "should merge object with userprofile if object belongs to user"
	(with-profile obj) => (merge obj {:user-profile user-a-profile}))

(fact "shoult not merge object with userprofile if object doesnt belong to any user"
	(with-profile {:key "val"}) => {:key "val"})
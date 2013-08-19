(ns whereabts-notify.db.replies-test
	(:use
		[whereabts-notify.db.replies]
		[whereabts-notify.db.replies-test-fixtures]
		[whereabts-notify.db.users-test-fixtures]
		[whereabts-notify.db.init]
		[midje.sweet]))

(def message-id (.toString (:message_id reply-a)))
(def excluding-user-id (:user_id reply-c))

(background (before :facts (setup-test-db)))

(fact "should find reply by its id"
	(find-reply "509d513f61395f0ebbd5e39a") => reply-a)

(fact "should return nil when trying to find reply that doesnt exist"
	(find-reply "509d513f61395f0ebbd5e666") => nil)

(fact "should find replies of a message, excluding replies of users"
	(find-replies-of-message-excluding-users 
		message-id [excluding-user-id]) => [reply-a])
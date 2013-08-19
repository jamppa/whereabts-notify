(ns whereabts-notify.replies-test
	(:use 
		[midje.sweet]
		[whereabts-notify.replies]
		[whereabts-notify.db.users]
		[whereabts-notify.db.replies]
		[whereabts-notify.gcm])
	(:import [org.bson.types ObjectId]))

(def message-id "509d513f61395f0ebbd5e60a")
(def user-id "509d513f61395f0ebbd5e39a")
(def reply-id "1234567")

(def reply {
	:replymessage "cool!" 
	:user-profile {:nick "jami"} 
	:user_id "321"})

(def reply-details {
	:message-id message-id 
	:user-id user-id 
	:reply-id reply-id})

(def reply-gcm-message {
		:registration_ids ["abc"] 
		:data {
			:type "TYPE_MESSAGE_REPLY" 
			:message-id message-id
			:reply-nick "jami"
			:reply-message "cool!"}})

(fact "should find reply with userprofile"
	(find-reply-with-user reply-id) => reply
	(provided 
		(find-reply reply-id) => reply :times 1
		(with-profile reply) => reply :times 1))

(fact "should notify message owner on new reply"
	(notify-message-owner reply-details) => anything
	(provided
		(find-reply-with-user reply-id) => reply :times 1
		(find-user-gcm-id user-id) => "abc" :times 1
		(notify-message-owner-on-reply reply "abc" message-id) => anything :times 1))

(fact "should not notify message owner on new reply if replying to own message"
	(notify-message-owner (merge reply-details {:user-id "321"})) => anything
	(provided
		(find-reply-with-user reply-id) => reply :times 1
		(find-user-gcm-id "321") => "abc" :times 1
		(notify-message-owner-on-reply reply "abc" "509d513f61395f0ebbd5e60a") => anything :times 0))

(fact "should send gcm-message when notifying message owner on reply"
	(notify-message-owner-on-reply reply "abc" message-id) => reply-gcm-message
	(provided
		(send-gcm-message reply-gcm-message) => reply-gcm-message :times 1))

(fact "should not send gcm-message when gcm-id is nil"
	(notify-message-owner-on-reply reply nil message-id) => anything
	(provided
		(send-gcm-message anything) => anything :times 0))

(fact "should notify other message repliers on new reply"
	(notify-message-repliers reply-details) => anything
	(provided
		(find-reply-with-user reply-id) => reply :times 1
		(find-respondents-gcm-ids message-id anything) => ["abc"] :times 1
		(send-gcm-message reply-gcm-message) => anything :times 1))

(fact "should find respondents gcm ids"
	(find-respondents-gcm-ids message-id [user-id]) => ["aBc"]
	(provided
		(find-replies-of-message-excluding-users message-id [user-id]) => [reply] :times 1
		(distinct-user-ids [reply]) => [user-id] :times 1
		(user-ids-to-gcm-ids [user-id]) => ["aBc"] :times 1))

(fact "should return distinct user ids as string from replies"
	(distinct-user-ids [
		{:user_id (ObjectId. "509d513f61395f0ebbd5e39a")} 
		{:user_id (ObjectId. "509d513f61395f0ebbd5e39a")}
		{:user_id (ObjectId. "509d513f61395f0ebbd5e40a")}]) => ["509d513f61395f0ebbd5e39a" "509d513f61395f0ebbd5e40a"])


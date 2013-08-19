(ns whereabts-notify.db.replies-test-fixtures
	(:use [whereabts-notify.db.users-test-fixtures])
	(:import [org.bson.types ObjectId]))

(def reply-a {
	:_id (ObjectId. "509d513f61395f0ebbd5e39a")
	:message_id (ObjectId. "509d513f61395f0ebbd5e60a")
	:user_id (:_id user-a)})

(def reply-b {
	:_id (ObjectId. "509d513f61395f0ebbd5e39b")
	:message_id (ObjectId. "509d513f61395f0ebbd5e60b")
	:user_id (:_id user-a)})

(def reply-c {
	:_id (ObjectId. "509d513f61395f0ebbd5e39c")
	:message_id (ObjectId. "509d513f61395f0ebbd5e60a")
	:user_id (:_id user-b)})

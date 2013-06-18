(ns whereabts-notify.db.users-test-fixtures
	(:import [org.bson.types ObjectId]))

(def user-a {
	:_id (ObjectId. "509d513f61395f0ebbd5e38a")
	:user-uuid "550e8400-e29b-41d4-a716-446655440000"
	:email "anonymous@whereabts.com"
	:role "anonymous"
	:created-at 1364642721970
	:last-seen-at 1364642721970
	:gcm-id "123AbC"})
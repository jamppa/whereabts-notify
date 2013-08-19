(ns whereabts-notify.db.replies
	(:use [monger.operators])
	(:require 
        [monger.core :as monger]
        [monger.collection :as monger-col])
	(:import [org.bson.types ObjectId]))

(def replies-coll "replies")

(defn- find-one-from-replies [query]
	(monger-col/find-one-as-map replies-coll query))

(defn find-reply [id]
	(find-one-from-replies {:_id (ObjectId. id)}))

(defn find-replies-of-message-excluding-users [message-id user-ids]
	(monger-col/find-maps 
		replies-coll {
			:message_id (ObjectId. message-id)
			:user_id {$nin user-ids}}))
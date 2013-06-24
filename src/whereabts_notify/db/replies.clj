(ns whereabts-notify.db.replies
	(:require 
        [monger.core :as monger]
        [monger.collection :as monger-col])
	(:import [org.bson.types ObjectId]))

(def replies-coll "replies")

(defn- find-one-from-replies [query]
	(monger-col/find-one-as-map replies-coll query))

(defn find-reply [id]
	(find-one-from-replies {:_id (ObjectId. id)}))
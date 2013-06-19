(ns whereabts-notify.db.users
	(:require 
        [monger.core :as monger]
        [monger.collection :as monger-col]
        [monger.json])
	(:import [org.bson.types ObjectId]))

(def users-coll "anonymous_users")

(defn- find-one-from-users [query]
	(monger-col/find-one-as-map users-coll query))

(defn find-user [id]
	(find-one-from-users {:_id (ObjectId. id)}))

(defn find-user-gcm-id [id]
	(let [user (find-user id)]
		(:gcm-id user)))
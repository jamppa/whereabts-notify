(ns whereabts-notify.db.users
	(:require 
        [monger.core :as monger]
        [monger.collection :as monger-col])
	(:import [org.bson.types ObjectId]))

(def users-coll "users")
(def user-profiles-coll "profiles")

(defn find-user [id]
	(let [user-oid (ObjectId. (.toString id))]
	(monger-col/find-one-as-map 
		users-coll {:_id user-oid})))

(defn find-user-gcm-id [id]
	(:gcm-id (find-user id)))

(defn find-profile-by-user-id [user-id]
	(let [user-oid (ObjectId. (.toString user-id))]
	(monger-col/find-one-as-map 
		user-profiles-coll {:user_id user-oid})))

(defn with-profile [obj]
	(if (contains? obj :user_id)
		(merge obj {:user-profile (find-profile-by-user-id (:user_id obj))})
		obj))

(defn user-ids-to-gcm-ids [user-ids]
	(remove nil? (map find-user-gcm-id user-ids)))
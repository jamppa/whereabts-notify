(ns whereabts-notify.db.init
	(:use 
		[whereabts-notify.db.conn]
		[whereabts-notify.db.users-test-fixtures])
	(:require 
		[monger.core :as monger]
		[monger.collection :as monger-col]))

(def test-db-name "whereabtsdb_test")

(defn insert-test-objects [coll objs]
	(doseq [obj objs]
		(monger-col/insert coll obj)))

(defn clear-collections [colls]
	(doseq [coll colls]
		(monger-col/remove coll)))

(defn populate-test-db []
	(clear-collections ["anonymous_users"])
	(insert-test-objects "anonymous_users" [user-a]))

(defn setup-test-db []
	(binding [*whereabts-db* test-db-name]
		(db-connect)
		(populate-test-db)))

(defn setup-db []
	(db-connect)
	(populate-test-db))
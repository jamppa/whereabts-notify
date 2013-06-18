(ns whereabts-notify.db.conn
	(:require 		
		[monger.core :as monger]))

(def ^:dynamic *whereabts-db* "whereabtsdb")

(defn db-connect []
    (monger/connect!)
    (monger/set-db! (monger/get-db *whereabts-db*)))
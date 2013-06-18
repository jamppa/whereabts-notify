(ns whereabts-notify.gcm
	(:require 
		[clj-http.client :as http]
		[clojure.data.json :as json]))

(def gcm-api "https://android.googleapis.com/gcm/send")
(def gcm-api-key "AIzaSyAGpvAB9yfop8iAlnmYZ7qs9hV1x7aOUfg")

(defn- as-json [obj]
	(json/write-str obj))

(defn- gcm-request [gcm-message]
	{
		:body (as-json gcm-message)
		:headers {"Authorization" (str "key=" gcm-api-key)}
		:content-type :json})

(defn gcm-message [registration-ids data]
	{
		:registration_ids registration-ids 
		:data data})

(defn send-gcm-message [gcm-message]
	(http/post gcm-api (gcm-request gcm-message)))


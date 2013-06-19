(ns whereabts-notify.replies
	(:use 
		[whereabts-notify.gcm]
		[whereabts-notify.db.users]))

(def reply-message-type 0)

(defn- replies-gcm-message [gcm-id details]
	(gcm-message [gcm-id] 
		{
			:type reply-message-type 
			:message-id (:replied-message details)}))

(defn notify-user-on-reply [details]
	(println details)
	(if-let [user-gcm-id (find-user-gcm-id (:user-to-notify details))]
		(send-gcm-message (replies-gcm-message user-gcm-id details))
		nil))
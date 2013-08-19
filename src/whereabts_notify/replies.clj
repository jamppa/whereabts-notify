(ns whereabts-notify.replies
	(:use 
		[whereabts-notify.gcm]
		[whereabts-notify.db.users]
		[whereabts-notify.db.replies])
	(:import [org.bson.types ObjectId]))

(def notify-owner-message-type "TYPE_MESSAGE_REPLY")
(def notify-replier-message-type "TYPE_MESSAGE_REPLY_REPLIER")

(defn- replies-gcm-message [gcm-ids reply message-id]
	(gcm-message gcm-ids
		{
			:type notify-owner-message-type 
			:message-id message-id
			:reply-nick (get-in reply [:user-profile :nick])
			:reply-message (get-in reply [:replymessage])}))

(defn distinct-user-ids [replies]
	(distinct 
		(map #(.toString (:user_id %)) replies)))

(defn find-respondents-gcm-ids [message-id excludes]
	(-> (find-replies-of-message-excluding-users message-id excludes)
		(distinct-user-ids)
		(user-ids-to-gcm-ids)))

(defn find-reply-with-user [reply-id]
	(-> reply-id
		(find-reply)
		(with-profile)))

(defn notify-message-owner-on-reply [reply user-gcm-id message-id]
	(when (every? identity [reply user-gcm-id message-id])			; there is a chance that user-gcm-id is missing
			(send-gcm-message 
				(replies-gcm-message [user-gcm-id] reply message-id))))

(defn notify-message-owner [reply-details]
	(let [reply (find-reply-with-user (:reply-id reply-details))
		  message-user (:user-id reply-details)
		  reply-user (.toString (:user_id reply))
		  message-user-gcm (find-user-gcm-id message-user)]

		(when-not (= message-user reply-user)
			(notify-message-owner-on-reply 
				reply message-user-gcm (:message-id reply-details)))))

(defn notify-message-repliers [reply-details]
	(let [reply (find-reply-with-user (:reply-id reply-details))
		  message-id (:message-id reply-details)
		  message-user-id (ObjectId. (:user-id reply-details))
		  reply-user-id (:user_id reply)
		  notifiable-gcm-ids (find-respondents-gcm-ids message-id [message-user-id reply-user-id])]

		  (send-gcm-message (replies-gcm-message notifiable-gcm-ids reply message-id))))
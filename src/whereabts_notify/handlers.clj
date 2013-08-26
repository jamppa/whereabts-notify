(ns whereabts-notify.handlers
	(:use 
		[whereabts-notify.replies]
		[validateur.validation])
	(:require [taoensso.carmine :as carmine]))

(def reply-channel "message.replies")
(def likes-channel "message.likes")

(def reply-details-validation
	(validation-set
		(presence-of :message-id)
		(presence-of :user-id)
		(presence-of :reply-id)))

(def like-details-validation
	(validation-set
		(presence-of :user-id)
		(presence-of :message-id)))

(defn- details-from-msg [msg]
	(get msg 2))

(defn- valid-details? [details]
	(valid? reply-details-validation details))

(defn reply-handler [msg]
	(let [reply-details (details-from-msg msg)]
	(when (valid-details? reply-details) 
		(notify-message-owner reply-details)
		(notify-message-repliers reply-details))
	msg))

(defn likes-handler [msg]
	)

(def message-handlers
	{
		reply-channel reply-handler
		likes-channel likes-handler})
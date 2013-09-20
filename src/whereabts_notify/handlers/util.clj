(ns whereabts-notify.handlers.util)

(defn channel-message [message]
	(get message 2))
(ns whereabts-notify.handlers
	(:require [taoensso.carmine :as carmine]))

(def reply-channel "replies")

(defn reply-handler [msg] nil)

(def message-handlers
	{reply-channel reply-handler})
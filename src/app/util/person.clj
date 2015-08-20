(ns app.util.person
  (:require [app.models.person :as person]))

(defn title-as-parent
  "Display title for person and partner on add child page,
  return the keys for look up in translation
  (person-display-key partner-display-key)"
  [person-info]
  (let [gender-map person/GENDERS_MAP
        person-gender (:gender person-info)]
    (cond
      (= person-gender (:male gender-map)) [:father-fullname :mother-fullname]
      (= person-gender (:female gender-map)) [:mother-fullname :father-fullname]
      (= person-gender (:gay gender-map)) [:mother-fullname :father-fullname]
      (= person-gender (:les gender-map)) [:father-fullname :mother-fullname]
      :else [:father-fullname :mother-fullname]
      )))

(defn determine-father-mother
  "Determine the input parent and partner to see who is the father, who is the mother"
  [parent partner]
  (let [parent-gender (:gender parent)
        gender-map person/GENDERS_MAP]
    (cond
      (= parent-gender (:male gender-map)) {:father parent :mother partner}
      (= parent-gender (:female gender-map)) {:father partner :mother parent}
      (= parent-gender (:gay gender-map)) {:father partner :mother parent}
      (= parent-gender (:les gender-map)) {:father parent :mother partner}
      :else {:father parent :mother partner}
      )))

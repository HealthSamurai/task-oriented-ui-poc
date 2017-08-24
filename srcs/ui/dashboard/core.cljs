(ns ui.dashboard.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require
   [reagent.core :as reagent]
   [ui.pages :as pages]
   [ui.routes :refer [href]]
   [re-frame.core :as rf]
   [ui.widgets :as wgt]
   [ui.styles :as styles]
   [clojure.string :as str]))

(defn index [params]
  [:div.container
   [:h1 "Dashboard"]])

(defn avatar []
  [:img.avatar
   {:src (str "https://randomuser.me/api/portraits/men/" (rand-int 100) ".jpg")}])

(defn user [name & [not-link?]]
  (if not-link? 
    [:span.assignee
     (avatar)
     name]
    [:a.assignee
     {:href (href :patients)}
     (avatar)
     name]))

(defn tasks [params]
  [:div
   (styles/style
    [:div
     [:.activities {:border-left-color "#8bc34a"}]
     [:.history {:border-left-color "#ddd"}]
     [:.time {:color "gray" :margin-right "20px"}]
     [:.item {:padding "5px 0"
              :color "#555"
              :display "block"}
      [:.fa {:color "gray"  :margin-right "10px"}]
      [:.title {:margin "0px"}]]])
   [:div.block.activities
    [:h1 "My tasks"]
    [:a.item 
     [wgt/icon :paper-plane-o]
     [:a.title {:href (href :tasks :claim-submit)} "Submit claims for 08/23/17"]
     (user "Nikolai Lee")]
    [:a.item 
     [wgt/icon :bar-chart-o]
     [:a.title {:href (href :tasks :claim-report)}
      "Billing report for July"]
     [:span
      " ›"]
     (user "Will Smith")]

    [:a.item 
     [wgt/icon :eye]
     [:a.title {:href (href :tasks :check-claim)}
      "Check claim for Drago Ivankovichi 08/31/17"]
     (user "John Ivanov")]
    [:a.item 
     [wgt/icon :bar-chart-o]
     [:a.title {:href (href :tasks :claim-report)}
      "Billing report for July"]
     (user "Will Smith")]
    [:a.item 
     [wgt/icon :free-code-camp]
     [:a.title {:href (href :tasks :claim-era)}
      "Check ERA for John Johanson for 08/01/17"]
     (user "Andrew Dunston")]
    ]
   
   [:br]
   [:div.block.history
   [:h3 "History"]

    (for [i (range 50)]
      (if (= i 4)
        [:a.item.expand
         [:div.title {:style {:background "#f1f1f1"}}
          [:span.time "10:15"]
          [wgt/rand-icon]
          [:a.title {:href (href :tasks :claim-report)}
           "Billing report for July"]
          (user "Andrew Dunston")]
         [:div.block
          [:h3 "Results of"]
          [:p "Contribute to shadow-cljs development by creating an account on GitHub. ... The compiler will produce one output file named public/assets/app/js/main.js .... :modules may also be used to generate .js file intended to be used as a Web Worker."]
          [:br]
          [:a {:href "#"} "Read more..."]
          ]

         ]
        
        [:a.item 
         [:span.time "10:15"]
         [wgt/rand-icon]
         [:a.title {:href (href :tasks :claim-report)}
          "Billing report for July"]
         (user "Andrew Dunston")]))]

   ])

(defn form []
  [:form.form.block
   (styles/style
    [:.form
     {:padding "20px"}
     [:.form-row {:margin-bottom "10px"}]
     [:label {:color "#888" :width "200px"  :text-align "right" :margin-right "10px"}]
     [:input {:border-top "none"
              :border-left "none"
              :border-right "none"
              :padding "5px 10px"
              :margin-left "10px"
              :border-bottom "1px solid #aaa"}]])
   [:h1 "Claim form"] 
   [:h3 "Patient Info"]
   [:div.form-row
    [:label "Patient Name"]
    [:input {:type "text"
             :placeholder "Family"}]
    [:input {:type "text"
             :placeholder "Given"}]]
   [:div.form-row
    [:label "Patient Address"]
    [:input {:type "text"
             :placeholder "Streat"}]
    [:input {:type "text"
             :placeholder "City"}]
    [:input {:type "text"
             :placeholder "ZIP"}]]
   [:h3 "Insurance Info"]
   [:div.form-row
    [:label "Insurance Type"]
    [:input {:type "text"
             :placeholder "Company"}]
    [:input {:type "text"
             :placeholder "Given"}]]
   [:div.form-row
    [:label "Insurance Type"]
    [:input {:type "text"
             :placeholder "Company"}]
    [:input {:type "text"
             :placeholder "Given"}]]
   [:div.form-row
    [:label "Insurance Type"]
    [:input {:type "text"
             :placeholder "Company"}]
    [:input {:type "text"
             :placeholder "Given"}]]

   [:h3 "Services"]
   [:div.form-row
    [:input {:type "text" :placeholder "Code"}]
    [:input {:type "text" :placeholder "Description"}]
    [:input {:type "text" :placeholder "Diagnosis"}]
    [:input {:type "text" :placeholder "Amount"}]
    " "
    [:button.btn.btn-default  " +"]]
   [:div.form-row
    [:input {:type "text" :placeholder "Code"}]
    [:input {:type "text" :placeholder "Description"}]
    [:input {:type "text" :placeholder "Diagnosis"}]
    [:input {:type "text" :placeholder "Amount"}]
    " "
    [:button.btn.btn-default  " +"]]
   [:div.form-row
    [:input {:type "text" :placeholder "Code"}]
    [:input {:type "text" :placeholder "Description"}]
    [:input {:type "text" :placeholder "Diagnosis"}]
    [:input {:type "text" :placeholder "Amount"}]
    " "
    [:button.btn.btn-default  " +"]]
   [:hr]
   [:button.btn.btn-primary  "Update"]

   ]
  )

(defn claim-check [params]
  [:div
   (styles/style
    [:div
     [:.form {:border-left-color "#aaa" :background-color "#f1f1f1"}]
     [:.task {:border-left-color "#0275d8"}]])
   [:div.task.block
    [:h3 [:b "Task:"] " Check Claim for " ]
    [:br]
    [:b "Start Time: "] "11:00"
    [:br]
    [:b "Status: "] "In Progress"
    [:br]
    [:b "Severity: "] "Normal"
    [:br]
    [:b "Assignee: " (user "Adolf Lungren")]
    [:a {:href "#/me"} "Assign to me"]
    " | "
    [:a {:href "#/me"} " Change"]]
   (form)])

(defn claim-submit [params]
  [:div
   (styles/style [:div
                  [:.claim {:border-left-color "#888"}]
                  [:.task {:border-left-color "#0275d8"}]])
   [:div.task.block
    [:h3 [:b "Task:"] " Submit claims for " [:i "08/01/17"]]
    [:b "Start Time: "] "11:00"
    [:br]
    [:b "Status: "] "In Progress"
    [:br]
    [:b "Severity: "] "Normal"
    [:br]
    [:b "Assignee: " (user "Adolf Lungren")]
    [:a {:href "#/me"} "Assign to me"]
    " | "
    [:a {:href "#/me"} " Change"]
    [:br]
    [:br]
    [:a.btn.btn-primary {:href (href "tasks")} "Submit"]]
   [:br]
   [:div.claim.block
    [:table.table
     [:thead
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:th "Ticket"]
       [:th "Name"]
       [:th "Insurance"]
       [:th "Services"]]]
     [:tbody
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:td "23333f"]
       [:td "Maria Fedotova"]
       [:td "Medicare"]
       [:td {:style {:padding 0}}
        [:table.table.table-striped.table-sm
         [:thead
          [:th "date"]
          [:th "procedure"]
          [:th "diagnosis"]
          [:th "charge"]]
         [:tbody
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]]]]]
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:td "23333f"]
       [:td "Maria Fedotova"]
       [:td "Medicare"]
       [:td {:style {:padding 0}}
        [:table.table.table-striped.table-sm
         [:thead
          [:th "date"]
          [:th "procedure"]
          [:th "diagnosis"]
          [:th "charge"]]
         [:tbody
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]]]]]
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:td "23333f"]
       [:td "Maria Fedotova"]
       [:td "Medicare"]
       [:td {:style {:padding 0}}
        [:table.table.table-striped.table-sm
         [:thead
          [:th "date"]
          [:th "procedure"]
          [:th "diagnosis"]
          [:th "charge"]]
         [:tbody
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]]]]]]]]])

(defn patient []
  [:div.patient.block
   (styles/style
    [:.patient
     {:border-left-color "#03A9F4"}
     [:b {:color "gray" :font-weight "300"}]
     [:img.avatar {:width "20px" :border "1px solid #ddd" :border-radius "50%"}]])

   [:h3 "Patient: " (avatar) " Ivan Mccalohen " [:span.small [wgt/icon :male] " 37 years old"]] 
   [:padding-left
    [:b "Phone: "] " 7777-77667"
    [:br]
    [:b "Address: "] " 1419 Westwood Blvd - Los Angeles CA"]])

(defn visit []
  [:div.visit.block
   (styles/style
    [:.visit
     {:border-left-color "#FFC107"}
     [:b {:color "gray" :font-weight "300"}]
     [:img.avatar {:width "18px"
                   :height "18px"
                   :margin "0 5px"
                   :border "1px solid #ddd"
                   :display "inline-block"
                   :border-radius "50%"}]])
   [:h3 "Visit: Cryotherapy" [:span.small " 08/01/2017"]] 
   [:p
    [:b "Physician: "] (user "Dr. House")]])

(defn claim-era [params]
  [:div
   (styles/style [:div
                  [:.pane {:width "900px"}]
                  [:.ctx {:position "fixed"
                          :width "400px"
                          :top 0
                          :right 0
                          :bottom 0
                          :padding "30px"
                          :background-color "#f6f6f6"}]
                  [:.era {:border-left-color "#F44336"}]
                  [:.task {:border-left-color "#0275d8"}]])
   [:div.pane
    [:div.task.block
     [:h3 "Review ERA for Ivan Maccalohen visit"]
     [:b "Start Time: "] "11:00"
     [:br]
     [:b "Status: "] "In Progress"
     [:br]
     [:b "Severity: "] "Normal"
     [:br]
     [:b "Assignee: " (user "Adolf Lungren")]
     [:a {:href "#/me"} "Assign to me"]
     " | "
     [:a {:href "#/me"} " Change"]]
    [:br]
    [:div.era.block
     [:h3 "Claim was rejected"]
     [:p "Regardless of how brilliant a medical biller is, they are guaranteed to come across rejections and denials from time to time. These terms are frequently used to discuss medical billing claims and are often used interchangeably by even the most experienced team members in the health field. However, a rejection differs vastly from a denial."]

     [:h3 "Possible actions:"]
     [:a.btn.btn-primary {:href (href "tasks")} "Submit"]]
    (form)]
   [:div.ctx
    [patient]
    [visit]]])

(defn task [params]
  [:div
   (get 
    {"claim-submit" [claim-submit params]
     "check-claim" [claim-check params]
     "claim-era" [claim-era params]
     }
    (:type params))])


(def nav-style
  [:div
   [:.navigator
    {:position "absolute"
     :left "90px"
     :top 0
     :bottom 0
     :padding-top "30px"
     :background "#f6f6f6"
     :padding-left "0px"
     :padding-right "10px"
     :width "190px"}
    [:h3 {:border-bottom "1px solid rgba(0,0,0,.15)!important"
          :font-size "20px"
          :line-height "40px"}]
    [:.item {:display "block"
             :padding "5px 10px"
             :color "#666"
             :font-weight 300}
     [:.fa {:width "24px"}]
     [:&.active {:color "#444" :font-weight "600"}]
     ]
    [:.switch {:background-color "#eee" :margin-top "10px"}
     [:.fa {:float "right" :color "#ccc" :padding-top "2px"}]
     ]
    [:.block {:padding-left "15px"}]
    [:a.assignee {:border "none" :padding-left 0}]]
   [:.pane {:margin-left "220px"}]])

(defn navigation [params]
  [:div
   (styles/style nav-style)
   [:div.navigator
    [:a.item {:href (href "claims")} [wgt/rand-icon] " Patients"]
    [:a.item {:href (href "claims")} [wgt/rand-icon] " Claims"]
    [:a.item {:href (href "claims")} [wgt/rand-icon] "Statistic"]
    [:a.item.active {:href (href "claims")} [wgt/rand-icon] " Visits"]
    [:hr]
    [user "Miky Mikluha"]
    [user "John Jonson"]
    [user "Alfred Muha"]
    [user "Pablo Picaso"]
    [user "Salvador Dali"]
    [user "John Jonson"]

    ]

   [:div.visits.pane
    [:h1 "Visits  " ]
    [:br]
    [:br]
    [:table.table
     [:tbody
      [:tr
       [:td.mute "08/01/17 10:00"]
       [:td (user "Luis Borhes")]
       [:td " Neurotoxins"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Joao Jilberto")]
       [:td "Neurotoxin injectable for skin rejuvenation"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Augustiniana Johanson")]
       [:td "Neurotoxin injectable for skin rejuvenation"]]
      [:tr
       [:td.mute "08/01/17 10:00"]
       [:td (user "Luis Borhes")]
       [:td " Neurotoxins"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Joao Jilberto")]
       [:td "Neurotoxin injectable for skin rejuvenation"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Augustiniana Johanson")]
       [:td "Neurotoxin injectable for skin rejuvenation"]]


      ]

     ]

    ]

   ])

(defn patients [params]
  [:div
   (styles/style nav-style)
   [:div.navigator {:style {:width "240px"}}
    [:a.item.switch {:href (href :navigation) } "Pt: " (user "AiBolit" true) [wgt/icon :chevron-down]]
    [:a.item {:href (href "claims")} [wgt/rand-icon] "Facesheet"]
    [:a.item {:href (href "claims")} [wgt/rand-icon] "Allergies"]
    [:a.item {:href (href "claims")} [wgt/rand-icon] "Observations"]
    [:a.item {:href (href "claims")} [wgt/rand-icon] "Medications"]
    [:a.item.switch "Visits: 02/03/20017 " [wgt/icon :chevron-down]]
    [:a.item {:href (href "claims")} [wgt/rand-icon] "Init. Assessment"]
    [:a.item {:href (href "claims")} [wgt/rand-icon] "Assessment"]
    [:a.item.active {:href (href "claims")} [wgt/rand-icon] "Notes"]
    [:a.item {:href (href "claims")} [wgt/rand-icon] "Discharge"]
    [:a.item {:href (href "claims")} [wgt/rand-icon] "Vitals"]]
   [:div.pane
    [patient]
    [visit]]])

(defn profile [params]
  [:div
   (styles/style [:.visits
                  [:.mute {:color "#888"}]
                  [:h3 [:.fa {:font-size "18px"
                              :z-index "100"
                              :position "relative" :left "20px" :margin-left "20px" :color "gray"}]]
                  [:.search {:border-top "none"
                             :border-right "none"
                             :padding-left "24px"
                             :position "relative"
                             :bottom "-1px"
                             :width "20em"
                             :border-bottom "1px solid #888"
                             :border-left "none"}
                   [:&:focus
                    {:border-top "none"
                     :border-right "none"
                     :border-bottom "1px solid blue"
                     :border-left "none"}]]
                  [:img.avatar {:width "18px"
                                :height "18px"
                                :margin "0 5px"
                                :border "1px solid #ddd"
                                :display "inline-block"
                                :border-radius "50%"}]
                  {:border-left "4px solid #4CAF50" :padding-left "20px" :margin-top "30px"}
                  [:h3 {:border-bottom "1px solid rgba(0,0,0,.15)!important"
                        :font-size "20px"
                        :line-height "40px"}]
                  [:.item {:display "block" :padding "5px 0" }
                   [:.fa {:width "24px"}]]])
   [:div.row
    [:div.col-6
     [:div.visits
      [:h3 "People  " [wgt/icon :search] [:input.search {:type "search" :placeholder "search..."}]]
      [:table.table
       [:tbody
        [:tr
         [:td (user "Luis Borhes") " " [wgt/icon :male]]
         [:td " Physician"]]
        [:tr
         [:td (user "Joao Jilberto") " " [wgt/icon :male]]
         [:td " Nurse"]]
        [:tr
         [:td (user "Augustiniana Johanson") " " [wgt/icon :female]]
         [:td " Biller"]]
        [:tr
         [:td (user "Luis Borhes") " " [wgt/icon :male]]
         [:td " Physician"]]]]]]
    [:div.col-6
     [:div.visits
      (styles/style [:.visits
                     [:.mute {:color "#888"}]
                     [:h3 [:.fa {:font-size "18px"
                                 :z-index "100"
                                 :position "relative" :left "20px" :margin-left "20px" :color "gray"}]]
                     [:.search {:border-top "none"
                                :border-right "none"
                                :padding-left "24px"
                                :position "relative"
                                :bottom "-1px"
                                :width "20em"
                                :border-bottom "1px solid #888"
                                :border-left "none"}
                      [:&:focus
                       {:border-top "none"
                        :border-right "none"
                        :border-bottom "1px solid blue"
                        :border-left "none"}]]
                     [:img.avatar {:width "18px"
                                   :height "18px"
                                   :margin "0 5px"
                                   :border "1px solid #ddd"
                                   :display "inline-block"
                                   :border-radius "50%"}]
                     {:border-left "4px solid #4CAF50" :padding-left "20px" :margin-top "30px"}
                     [:h3 {:border-bottom "1px solid rgba(0,0,0,.15)!important"
                           :font-size "20px"
                           :line-height "40px"}]
                     [:.item {:display "block" :padding "5px 0" }
                      [:.fa {:width "24px"}]]])
      [:h3 "Teams  " [wgt/icon :search]
       [:input.search {:type "search" :placeholder "search..."}]]
      [:table.table
       [:tbody
        [:tr
         [:td [:a {:href (href :teams :physicians)} "Billers"]]
         [:td " Neurotoxins"]]
        [:tr
         [:td [:a {:href (href :teams :physicians)} "Physician"]]
         [:td "Neurotoxin injectable for skin rejuvenation"]]
        [:tr
         [:td [:a {:href (href :teams :physicians)} "Nurses"]]
         [:td "Neurotoxin injectable for skin rejuvenation"]]]]]]
    ]
   

   
   [:div.visits
    (styles/style [:.visits
                   [:.mute {:color "#888"}]
                   [:h3 [:.fa {:font-size "18px"
                               :z-index "100"
                               :position "relative" :left "20px" :margin-left "20px" :color "gray"}]]
                   [:.search {:border-top "none"
                              :border-right "none"
                              :padding-left "24px"
                              :position "relative"
                              :bottom "-1px"
                              :width "20em"
                              :border-bottom "1px solid #888"
                              :border-left "none"}
                    [:&:focus
                     {:border-top "none"
                      :border-right "none"
                      :border-bottom "1px solid blue"
                      :border-left "none"}]
                    ]
                   [:img.avatar {:width "18px"
                                 :height "18px"
                                 :margin "0 5px"
                                 :border "1px solid #ddd"
                                 :display "inline-block"
                                 :border-radius "50%"}]
                   {:border-left "4px solid #4CAF50" :padding-left "20px" :margin-top "30px"}
                   [:h3 {:border-bottom "1px solid rgba(0,0,0,.15)!important"
                         :font-size "20px"
                         :line-height "40px"}]
                   [:.item {:display "block" :padding "5px 0" }
                    [:.fa {:width "24px"}]]])
    [:h3 "Patients  " [wgt/icon :search] [:input.search {:type "search" :placeholder "search..."}]]
    [:table.table
     [:tbody
      [:tr
       [:td (user "Luis Borhes") " " [wgt/icon :male]]]
      [:tr
       [:td (user "Joao Jilberto") " " [wgt/icon :male]]]
      [:tr
       [:td (user "Augustiniana Johanson") " " [wgt/icon :female]]]
      [:tr
       [:td (user "Luis Borhes") " " [wgt/icon :male]]]
      [:tr
       [:td (user "Joao Jilberto") " " [wgt/icon :male]]]
      [:tr
       [:td (user "Augustiniana Johanson") " " [wgt/icon :female]]]]]]])

(pages/reg-page :dashboard/index index)
(pages/reg-page :dashboard/tasks tasks)
(pages/reg-page :dashboard/task task)
(pages/reg-page :dashboard/navigation navigation)
(pages/reg-page :dashboard/patients patients)
(pages/reg-page :dashboard/profile profile)

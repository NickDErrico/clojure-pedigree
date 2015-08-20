(ns app.i18n.en)

(def dict
  {:en {:user "User"
        :error {:not-login "Not Login"}

        :layout {:homepage "Homepage"
                 :treepage "Pedigree Tree"
                 :members "Members"
                 :pedigree-tree "Pedigree tree"
                 :pedigree-history "Pedigree History"
                 :contact "Contact"
                 :hello "Hello"
                 :login "Login"
                 :title "Tran Van Pedigree"
                 :head-line "Keep the value for the children"}

        ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
        ;; pages
        :page-index {:preface "Preface"
                     :members "Members"
                     :news "News"
                     :more-news "More news..."
                     :images "Images"
                     :pedigree-tree "Pedigree tree"
                     :detail-tree "View detail pedigree tree"
                     :other-links "Other links"}

        :page-add-child {:father-fullname "Father full name"
                         :mother-fullname "Mother full name"
                         :child-fullname "Child full name"
                         :person-unknown "Không rõ"
                         :child-order-title "Là con thứ bao nhiêu trong gia đình?"
                         :submit "Thêm con"

                         :error-parent-not-found "Parent not found"}

        :login {:invalid-error "Username or Password incorrect"}
        }})

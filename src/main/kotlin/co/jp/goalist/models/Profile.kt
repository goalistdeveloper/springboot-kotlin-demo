package co.jp.goalist.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("profiles")
data class Profile(@Id val id: String?, val profilePhotoURL: String?, val address: String = "")
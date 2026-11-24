/*
 * Generated from AiCode/VantaCode Android string resources (zh: values/, en: values-en/).
 * Regenerate with tooling-genstrings.js. GPL-3.0, see LICENSE. Do not edit by hand.
 */
package com.vantacode.core.ui

import androidx.compose.runtime.Composable
import java.util.Locale
import java.util.concurrent.atomic.AtomicReference

object Strings {
    // For MVP, just an atomic ref to avoid Compose snapshot interference
    internal val _current = AtomicReference("zh")

    /** 获取当前语言，返回 "zh" 或 "en"。 */
    val current: String
        get() = _current.get()

    fun formatted(template: String, vararg args: Any?): String =
        if (args.isEmpty()) template else String.format(Locale.ROOT, template, *args)

    internal fun catalog(): Map<String, String> = if (_current.get() == "en") en else zh

    val about_auto_check_update: String get() = catalog().getValue("about_auto_check_update")
    val about_auto_check_update_desc: String get() = catalog().getValue("about_auto_check_update_desc")
    val about_check_failed: String get() = catalog().getValue("about_check_failed")
    val about_check_update: String get() = catalog().getValue("about_check_update")
    val about_checking_update: String get() = catalog().getValue("about_checking_update")
    val about_description: String get() = catalog().getValue("about_description")
    val about_download: String get() = catalog().getValue("about_download")
    val about_github_repo: String get() = catalog().getValue("about_github_repo")
    val about_later: String get() = catalog().getValue("about_later")
    val about_license: String get() = catalog().getValue("about_license")
    val about_network_error: String get() = catalog().getValue("about_network_error")
    val about_new_version_found: String get() = catalog().getValue("about_new_version_found")
    val about_no_changelog: String get() = catalog().getValue("about_no_changelog")
    val about_ok: String get() = catalog().getValue("about_ok")
    val about_qq_group: String get() = catalog().getValue("about_qq_group")
    val about_qq_group_copied: String get() = catalog().getValue("about_qq_group_copied")
    val about_parse_version_failed: String get() = catalog().getValue("about_parse_version_failed")
    val about_up_to_date: String get() = catalog().getValue("about_up_to_date")
    val about_up_to_date_detail: String get() = catalog().getValue("about_up_to_date_detail")
    val about_update_apk_ready: String get() = catalog().getValue("about_update_apk_ready")
    val about_update_background: String get() = catalog().getValue("about_update_background")
    val about_update_browser: String get() = catalog().getValue("about_update_browser")
    val about_update_channel: String get() = catalog().getValue("about_update_channel")
    val about_update_download_failed: String get() = catalog().getValue("about_update_download_failed")
    val about_update_download_failed_hint: String get() = catalog().getValue("about_update_download_failed_hint")
    val about_update_download_install: String get() = catalog().getValue("about_update_download_install")
    val about_update_download_size: String get() = catalog().getValue("about_update_download_size")
    val about_update_downloading: String get() = catalog().getValue("about_update_downloading")
    val about_update_install: String get() = catalog().getValue("about_update_install")
    val about_update_install_failed: String get() = catalog().getValue("about_update_install_failed")
    val about_update_mirror: String get() = catalog().getValue("about_update_mirror")
    val about_update_mirror_default: String get() = catalog().getValue("about_update_mirror_default")
    val about_update_mirror_desc: String get() = catalog().getValue("about_update_mirror_desc")
    val about_update_mirror_invalid: String get() = catalog().getValue("about_update_mirror_invalid")
    val about_update_mirror_preset_cdn: String get() = catalog().getValue("about_update_mirror_preset_cdn")
    val about_update_mirror_preset_direct: String get() = catalog().getValue("about_update_mirror_preset_direct")
    val about_update_mirror_preset_v6: String get() = catalog().getValue("about_update_mirror_preset_v6")
    val about_update_need_permission: String get() = catalog().getValue("about_update_need_permission")
    val about_update_retry: String get() = catalog().getValue("about_update_retry")
    val about_update_verify_not_apk: String get() = catalog().getValue("about_update_verify_not_apk")
    val about_update_verify_not_newer: String get() = catalog().getValue("about_update_verify_not_newer")
    val about_update_verify_package: String get() = catalog().getValue("about_update_verify_package")
    val about_update_verify_signature: String get() = catalog().getValue("about_update_verify_signature")
    val about_version: String get() = catalog().getValue("about_version")
    val agent_compaction_failed: String get() = catalog().getValue("agent_compaction_failed")
    val agent_complete_notification_background_body: String get() = catalog().getValue("agent_complete_notification_background_body")
    val agent_complete_notification_background_multi_body: String get() = catalog().getValue("agent_complete_notification_background_multi_body")
    val agent_complete_notification_body: String get() = catalog().getValue("agent_complete_notification_body")
    val agent_complete_notification_title: String get() = catalog().getValue("agent_complete_notification_title")
    val agent_context_no_compaction: String get() = catalog().getValue("agent_context_no_compaction")
    val agent_input_budget_exceeded: String get() = catalog().getValue("agent_input_budget_exceeded")
    val agent_model_not_selected: String get() = catalog().getValue("agent_model_not_selected")
    val agent_stop_context_exceeded: String get() = catalog().getValue("agent_stop_context_exceeded")
    val agent_stop_refusal: String get() = catalog().getValue("agent_stop_refusal")
    val agent_stopped_by_user: String get() = catalog().getValue("agent_stopped_by_user")
    val agent_tool_executing: String get() = catalog().getValue("agent_tool_executing")
    val agreement_accept: String get() = catalog().getValue("agreement_accept")
    val agreement_decline: String get() = catalog().getValue("agreement_decline")
    val agreement_notice_conjunction: String get() = catalog().getValue("agreement_notice_conjunction")
    val agreement_notice_prefix: String get() = catalog().getValue("agreement_notice_prefix")
    val agreement_notice_suffix: String get() = catalog().getValue("agreement_notice_suffix")
    val agreement_privacy: String get() = catalog().getValue("agreement_privacy")
    val agreement_privacy_link: String get() = catalog().getValue("agreement_privacy_link")
    val agreement_privacy_url: String get() = catalog().getValue("agreement_privacy_url")
    val agreement_terms: String get() = catalog().getValue("agreement_terms")
    val agreement_terms_link: String get() = catalog().getValue("agreement_terms_link")
    val agreement_terms_url: String get() = catalog().getValue("agreement_terms_url")
    val agreement_title: String get() = catalog().getValue("agreement_title")
    val app_name: String get() = catalog().getValue("app_name")
    val ask_confirm: String get() = catalog().getValue("ask_confirm")
    val ask_confirm_title: String get() = catalog().getValue("ask_confirm_title")
    val ask_custom_answer: String get() = catalog().getValue("ask_custom_answer")
    val ask_input_hint: String get() = catalog().getValue("ask_input_hint")
    val ask_supplement: String get() = catalog().getValue("ask_supplement")
    val backup_actions: String get() = catalog().getValue("backup_actions")
    val backup_data_app_settings: String get() = catalog().getValue("backup_data_app_settings")
    val backup_data_chat_history: String get() = catalog().getValue("backup_data_chat_history")
    val backup_data_mcp: String get() = catalog().getValue("backup_data_mcp")
    val backup_data_permissions: String get() = catalog().getValue("backup_data_permissions")
    val backup_data_remote: String get() = catalog().getValue("backup_data_remote")
    val backup_data_workspace: String get() = catalog().getValue("backup_data_workspace")
    val backup_export_btn: String get() = catalog().getValue("backup_export_btn")
    val backup_export_failed: String get() = catalog().getValue("backup_export_failed")
    val backup_export_subtitle: String get() = catalog().getValue("backup_export_subtitle")
    val backup_export_title: String get() = catalog().getValue("backup_export_title")
    val backup_exported: String get() = catalog().getValue("backup_exported")
    val backup_import_btn: String get() = catalog().getValue("backup_import_btn")
    val backup_import_done: String get() = catalog().getValue("backup_import_done")
    val backup_import_failed: String get() = catalog().getValue("backup_import_failed")
    val backup_import_subtitle: String get() = catalog().getValue("backup_import_subtitle")
    val backup_import_title: String get() = catalog().getValue("backup_import_title")
    val backup_net_access_key: String get() = catalog().getValue("backup_net_access_key")
    val backup_net_add_target: String get() = catalog().getValue("backup_net_add_target")
    val backup_net_base_path: String get() = catalog().getValue("backup_net_base_path")
    val backup_net_base_path_hint: String get() = catalog().getValue("backup_net_base_path_hint")
    val backup_net_bucket: String get() = catalog().getValue("backup_net_bucket")
    val backup_net_delete_failed: String get() = catalog().getValue("backup_net_delete_failed")
    val backup_net_delete_target_confirm: String get() = catalog().getValue("backup_net_delete_target_confirm")
    val backup_net_edit_target: String get() = catalog().getValue("backup_net_edit_target")
    val backup_net_endpoint: String get() = catalog().getValue("backup_net_endpoint")
    val backup_net_endpoint_hint: String get() = catalog().getValue("backup_net_endpoint_hint")
    val backup_net_list_failed: String get() = catalog().getValue("backup_net_list_failed")
    val backup_net_loading: String get() = catalog().getValue("backup_net_loading")
    val backup_net_name: String get() = catalog().getValue("backup_net_name")
    val backup_net_no_target: String get() = catalog().getValue("backup_net_no_target")
    val backup_net_no_target_hint: String get() = catalog().getValue("backup_net_no_target_hint")
    val backup_net_password: String get() = catalog().getValue("backup_net_password")
    val backup_net_path_style: String get() = catalog().getValue("backup_net_path_style")
    val backup_net_region: String get() = catalog().getValue("backup_net_region")
    val backup_net_remote_delete: String get() = catalog().getValue("backup_net_remote_delete")
    val backup_net_remote_delete_confirm: String get() = catalog().getValue("backup_net_remote_delete_confirm")
    val backup_net_remote_empty: String get() = catalog().getValue("backup_net_remote_empty")
    val backup_net_remote_list_title: String get() = catalog().getValue("backup_net_remote_list_title")
    val backup_net_remote_restore: String get() = catalog().getValue("backup_net_remote_restore")
    val backup_net_restore: String get() = catalog().getValue("backup_net_restore")
    val backup_net_restore_subtitle: String get() = catalog().getValue("backup_net_restore_subtitle")
    val backup_net_secret_key: String get() = catalog().getValue("backup_net_secret_key")
    val backup_net_section: String get() = catalog().getValue("backup_net_section")
    val backup_net_session_token: String get() = catalog().getValue("backup_net_session_token")
    val backup_net_targets: String get() = catalog().getValue("backup_net_targets")
    val backup_net_test_connection: String get() = catalog().getValue("backup_net_test_connection")
    val backup_net_test_failed: String get() = catalog().getValue("backup_net_test_failed")
    val backup_net_test_success: String get() = catalog().getValue("backup_net_test_success")
    val backup_net_testing: String get() = catalog().getValue("backup_net_testing")
    val backup_net_type_s3: String get() = catalog().getValue("backup_net_type_s3")
    val backup_net_type_webdav: String get() = catalog().getValue("backup_net_type_webdav")
    val backup_net_upload: String get() = catalog().getValue("backup_net_upload")
    val backup_net_upload_done: String get() = catalog().getValue("backup_net_upload_done")
    val backup_net_upload_failed: String get() = catalog().getValue("backup_net_upload_failed")
    val backup_net_upload_password_hint: String get() = catalog().getValue("backup_net_upload_password_hint")
    val backup_net_upload_password_required: String get() = catalog().getValue("backup_net_upload_password_required")
    val backup_net_upload_short: String get() = catalog().getValue("backup_net_upload_short")
    val backup_net_upload_subtitle: String get() = catalog().getValue("backup_net_upload_subtitle")
    val backup_net_uploading: String get() = catalog().getValue("backup_net_uploading")
    val backup_net_username: String get() = catalog().getValue("backup_net_username")
    val backup_operation_failed: String get() = catalog().getValue("backup_operation_failed")
    val backup_password_hint: String get() = catalog().getValue("backup_password_hint")
    val backup_password_input: String get() = catalog().getValue("backup_password_input")
    val backup_password_label: String get() = catalog().getValue("backup_password_label")
    val backup_password_optional_hint: String get() = catalog().getValue("backup_password_optional_hint")
    val backup_processing: String get() = catalog().getValue("backup_processing")
    val backup_processing_data: String get() = catalog().getValue("backup_processing_data")
    val backup_read_failed: String get() = catalog().getValue("backup_read_failed")
    val backup_restore_workspaces_confirm: String get() = catalog().getValue("backup_restore_workspaces_confirm")
    val backup_restore_workspaces_files: String get() = catalog().getValue("backup_restore_workspaces_files")
    val backup_restore_workspaces_select_all: String get() = catalog().getValue("backup_restore_workspaces_select_all")
    val backup_restore_workspaces_title: String get() = catalog().getValue("backup_restore_workspaces_title")
    val backup_restore_workspaces_warning: String get() = catalog().getValue("backup_restore_workspaces_warning")
    val backup_restored_data: String get() = catalog().getValue("backup_restored_data")
    val backup_select_data: String get() = catalog().getValue("backup_select_data")
    val backup_set_password: String get() = catalog().getValue("backup_set_password")
    val backup_settings_covered: String get() = catalog().getValue("backup_settings_covered")
    val backup_stat_chat_messages: String get() = catalog().getValue("backup_stat_chat_messages")
    val backup_stat_chat_sessions: String get() = catalog().getValue("backup_stat_chat_sessions")
    val backup_stat_mcp_servers: String get() = catalog().getValue("backup_stat_mcp_servers")
    val backup_stat_permission_rules: String get() = catalog().getValue("backup_stat_permission_rules")
    val backup_stat_providers: String get() = catalog().getValue("backup_stat_providers")
    val backup_stat_remote_connections: String get() = catalog().getValue("backup_stat_remote_connections")
    val backup_stat_remote_mounts: String get() = catalog().getValue("backup_stat_remote_mounts")
    val backup_stat_todo_items: String get() = catalog().getValue("backup_stat_todo_items")
    val backup_stat_workspace_files: String get() = catalog().getValue("backup_stat_workspace_files")
    val backup_tab_local: String get() = catalog().getValue("backup_tab_local")
    val backup_workspace_git_hint: String get() = catalog().getValue("backup_workspace_git_hint")
    val backup_write_failed: String get() = catalog().getValue("backup_write_failed")
    val backup_wrong_password: String get() = catalog().getValue("backup_wrong_password")
    val browser_address_hint: String get() = catalog().getValue("browser_address_hint")
    val browser_back: String get() = catalog().getValue("browser_back")
    val browser_close_tab: String get() = catalog().getValue("browser_close_tab")
    val browser_devtools: String get() = catalog().getValue("browser_devtools")
    val browser_devtools_desc: String get() = catalog().getValue("browser_devtools_desc")
    val browser_devtools_title: String get() = catalog().getValue("browser_devtools_title")
    val browser_dialog_alert_title: String get() = catalog().getValue("browser_dialog_alert_title")
    val browser_dialog_confirm_title: String get() = catalog().getValue("browser_dialog_confirm_title")
    val browser_dialog_prompt_title: String get() = catalog().getValue("browser_dialog_prompt_title")
    val browser_empty_click_hint: String get() = catalog().getValue("browser_empty_click_hint")
    val browser_empty_hint: String get() = catalog().getValue("browser_empty_hint")
    val browser_forward: String get() = catalog().getValue("browser_forward")
    val browser_menu: String get() = catalog().getValue("browser_menu")
    val browser_menu_general_section: String get() = catalog().getValue("browser_menu_general_section")
    val browser_menu_title: String get() = catalog().getValue("browser_menu_title")
    val browser_new_tab: String get() = catalog().getValue("browser_new_tab")
    val browser_night_mode_desc: String get() = catalog().getValue("browser_night_mode_desc")
    val browser_night_mode_off: String get() = catalog().getValue("browser_night_mode_off")
    val browser_night_mode_on: String get() = catalog().getValue("browser_night_mode_on")
    val browser_night_mode_title: String get() = catalog().getValue("browser_night_mode_title")
    val browser_not_ready: String get() = catalog().getValue("browser_not_ready")
    val browser_reload: String get() = catalog().getValue("browser_reload")
    val browser_tab_default_title: String get() = catalog().getValue("browser_tab_default_title")
    val browser_tabs_count: String get() = catalog().getValue("browser_tabs_count")
    val browser_tabs_manage: String get() = catalog().getValue("browser_tabs_manage")
    val browser_title: String get() = catalog().getValue("browser_title")
    val browser_ua_android_phone: String get() = catalog().getValue("browser_ua_android_phone")
    val browser_ua_android_phone_desc: String get() = catalog().getValue("browser_ua_android_phone_desc")
    val browser_ua_android_tablet: String get() = catalog().getValue("browser_ua_android_tablet")
    val browser_ua_android_tablet_desc: String get() = catalog().getValue("browser_ua_android_tablet_desc")
    val browser_ua_applied_toast: String get() = catalog().getValue("browser_ua_applied_toast")
    val browser_ua_custom: String get() = catalog().getValue("browser_ua_custom")
    val browser_ua_custom_desc: String get() = catalog().getValue("browser_ua_custom_desc")
    val browser_ua_custom_dialog_title: String get() = catalog().getValue("browser_ua_custom_dialog_title")
    val browser_ua_custom_input_hint: String get() = catalog().getValue("browser_ua_custom_input_hint")
    val browser_ua_default: String get() = catalog().getValue("browser_ua_default")
    val browser_ua_default_desc: String get() = catalog().getValue("browser_ua_default_desc")
    val browser_ua_desc: String get() = catalog().getValue("browser_ua_desc")
    val browser_ua_desktop_pc: String get() = catalog().getValue("browser_ua_desktop_pc")
    val browser_ua_desktop_pc_desc: String get() = catalog().getValue("browser_ua_desktop_pc_desc")
    val browser_ua_ipad: String get() = catalog().getValue("browser_ua_ipad")
    val browser_ua_ipad_desc: String get() = catalog().getValue("browser_ua_ipad_desc")
    val browser_ua_iphone: String get() = catalog().getValue("browser_ua_iphone")
    val browser_ua_iphone_desc: String get() = catalog().getValue("browser_ua_iphone_desc")
    val browser_ua_section: String get() = catalog().getValue("browser_ua_section")
    val chat_action_copy: String get() = catalog().getValue("chat_action_copy")
    val chat_action_delete: String get() = catalog().getValue("chat_action_delete")
    val chat_action_edit: String get() = catalog().getValue("chat_action_edit")
    val chat_action_fork: String get() = catalog().getValue("chat_action_fork")
    val chat_add_attachment: String get() = catalog().getValue("chat_add_attachment")
    val chat_approve_and_implement: String get() = catalog().getValue("chat_approve_and_implement")
    val chat_attachment_max: String get() = catalog().getValue("chat_attachment_max")
    val chat_attachment_open: String get() = catalog().getValue("chat_attachment_open")
    val chat_attachment_prefix: String get() = catalog().getValue("chat_attachment_prefix")
    val chat_attachment_preview: String get() = catalog().getValue("chat_attachment_preview")
    val chat_attachment_uploaded: String get() = catalog().getValue("chat_attachment_uploaded")
    val chat_attachment_uploaded_partial: String get() = catalog().getValue("chat_attachment_uploaded_partial")
    val chat_attachment_uploading: String get() = catalog().getValue("chat_attachment_uploading")
    val chat_batch_delete_confirm: String get() = catalog().getValue("chat_batch_delete_confirm")
    val chat_batch_delete_title: String get() = catalog().getValue("chat_batch_delete_title")
    val chat_batch_select: String get() = catalog().getValue("chat_batch_select")
    val chat_bg_command_done: String get() = catalog().getValue("chat_bg_command_done")
    val chat_bg_commands_done: String get() = catalog().getValue("chat_bg_commands_done")
    val chat_bg_commands_partial_failed: String get() = catalog().getValue("chat_bg_commands_partial_failed")
    val chat_cache_hit_rate: String get() = catalog().getValue("chat_cache_hit_rate")
    val chat_camera_file_failed: String get() = catalog().getValue("chat_camera_file_failed")
    val chat_compaction_failed: String get() = catalog().getValue("chat_compaction_failed")
    val chat_compressing_context: String get() = catalog().getValue("chat_compressing_context")
    val chat_connecting_remote: String get() = catalog().getValue("chat_connecting_remote")
    val chat_context_compressed: String get() = catalog().getValue("chat_context_compressed")
    val chat_context_usage: String get() = catalog().getValue("chat_context_usage")
    val chat_context_usage_estimated: String get() = catalog().getValue("chat_context_usage_estimated")
    val chat_continue_feedback: String get() = catalog().getValue("chat_continue_feedback")
    val chat_copied: String get() = catalog().getValue("chat_copied")
    val chat_copy: String get() = catalog().getValue("chat_copy")
    val chat_delete_session: String get() = catalog().getValue("chat_delete_session")
    val chat_delete_session_confirm: String get() = catalog().getValue("chat_delete_session_confirm")
    val chat_edit_dialog_hint: String get() = catalog().getValue("chat_edit_dialog_hint")
    val chat_edit_dialog_title: String get() = catalog().getValue("chat_edit_dialog_title")
    val chat_error_title: String get() = catalog().getValue("chat_error_title")
    val chat_export_session: String get() = catalog().getValue("chat_export_session")
    val chat_export_session_done: String get() = catalog().getValue("chat_export_session_done")
    val chat_export_session_failed: String get() = catalog().getValue("chat_export_session_failed")
    val chat_fork_title: String get() = catalog().getValue("chat_fork_title")
    val chat_goal_clear: String get() = catalog().getValue("chat_goal_clear")
    val chat_goal_label: String get() = catalog().getValue("chat_goal_label")
    val chat_goal_pause: String get() = catalog().getValue("chat_goal_pause")
    val chat_goal_resume: String get() = catalog().getValue("chat_goal_resume")
    val chat_goal_status_active: String get() = catalog().getValue("chat_goal_status_active")
    val chat_goal_status_blocked: String get() = catalog().getValue("chat_goal_status_blocked")
    val chat_goal_status_completed: String get() = catalog().getValue("chat_goal_status_completed")
    val chat_goal_status_exhausted: String get() = catalog().getValue("chat_goal_status_exhausted")
    val chat_goal_status_paused: String get() = catalog().getValue("chat_goal_status_paused")
    val chat_history: String get() = catalog().getValue("chat_history")
    val chat_image_too_large: String get() = catalog().getValue("chat_image_too_large")
    val chat_input_expand: String get() = catalog().getValue("chat_input_expand")
    val chat_input_fullscreen_title: String get() = catalog().getValue("chat_input_fullscreen_title")
    val chat_input_hint: String get() = catalog().getValue("chat_input_hint")
    val chat_input_placeholder: String get() = catalog().getValue("chat_input_placeholder")
    val chat_key_switched: String get() = catalog().getValue("chat_key_switched")
    val chat_mention_empty: String get() = catalog().getValue("chat_mention_empty")
    val chat_mention_error: String get() = catalog().getValue("chat_mention_error")
    val chat_mention_loading: String get() = catalog().getValue("chat_mention_loading")
    val chat_mention_unavailable: String get() = catalog().getValue("chat_mention_unavailable")
    val chat_more_options: String get() = catalog().getValue("chat_more_options")
    val chat_new_session: String get() = catalog().getValue("chat_new_session")
    val chat_new_session_btn: String get() = catalog().getValue("chat_new_session_btn")
    val chat_no_model_selected: String get() = catalog().getValue("chat_no_model_selected")
    val chat_no_models_hint: String get() = catalog().getValue("chat_no_models_hint")
    val chat_no_remote_connection: String get() = catalog().getValue("chat_no_remote_connection")
    val chat_no_sessions_hint: String get() = catalog().getValue("chat_no_sessions_hint")
    val chat_open_apk_permission: String get() = catalog().getValue("chat_open_apk_permission")
    val chat_open_browser: String get() = catalog().getValue("chat_open_browser")
    val chat_open_file_missing: String get() = catalog().getValue("chat_open_file_missing")
    val chat_open_file_no_app: String get() = catalog().getValue("chat_open_file_no_app")
    val chat_open_file_unshareable: String get() = catalog().getValue("chat_open_file_unshareable")
    val chat_open_git: String get() = catalog().getValue("chat_open_git")
    val chat_open_sidebar: String get() = catalog().getValue("chat_open_sidebar")
    val chat_open_terminal: String get() = catalog().getValue("chat_open_terminal")
    val chat_perm_always_allow: String get() = catalog().getValue("chat_perm_always_allow")
    val chat_perm_always_prefix: String get() = catalog().getValue("chat_perm_always_prefix")
    val chat_perm_always_tool_desc: String get() = catalog().getValue("chat_perm_always_tool_desc")
    val chat_perm_deny: String get() = catalog().getValue("chat_perm_deny")
    val chat_perm_session_label: String get() = catalog().getValue("chat_perm_session_label")
    val chat_perm_single_use_desc: String get() = catalog().getValue("chat_perm_single_use_desc")
    val chat_pin_session: String get() = catalog().getValue("chat_pin_session")
    val chat_placeholder: String get() = catalog().getValue("chat_placeholder")
    val chat_plan_completed: String get() = catalog().getValue("chat_plan_completed")
    val chat_queue_drag: String get() = catalog().getValue("chat_queue_drag")
    val chat_queue_edit: String get() = catalog().getValue("chat_queue_edit")
    val chat_queue_hint: String get() = catalog().getValue("chat_queue_hint")
    val chat_queue_interject: String get() = catalog().getValue("chat_queue_interject")
    val chat_queue_remove: String get() = catalog().getValue("chat_queue_remove")
    val chat_queue_title: String get() = catalog().getValue("chat_queue_title")
    val chat_read_file_failed: String get() = catalog().getValue("chat_read_file_failed")
    val chat_reasoning_cannot_disable: String get() = catalog().getValue("chat_reasoning_cannot_disable")
    val chat_reasoning_default_tag: String get() = catalog().getValue("chat_reasoning_default_tag")
    val chat_reasoning_effective: String get() = catalog().getValue("chat_reasoning_effective")
    val chat_reasoning_effective_tag: String get() = catalog().getValue("chat_reasoning_effective_tag")
    val chat_reasoning_effort: String get() = catalog().getValue("chat_reasoning_effort")
    val chat_reasoning_effort_high: String get() = catalog().getValue("chat_reasoning_effort_high")
    val chat_reasoning_effort_low: String get() = catalog().getValue("chat_reasoning_effort_low")
    val chat_reasoning_effort_max: String get() = catalog().getValue("chat_reasoning_effort_max")
    val chat_reasoning_effort_medium: String get() = catalog().getValue("chat_reasoning_effort_medium")
    val chat_reasoning_effort_minimal: String get() = catalog().getValue("chat_reasoning_effort_minimal")
    val chat_reasoning_effort_none: String get() = catalog().getValue("chat_reasoning_effort_none")
    val chat_reasoning_effort_xhigh: String get() = catalog().getValue("chat_reasoning_effort_xhigh")
    val chat_reasoning_endpoint_weak: String get() = catalog().getValue("chat_reasoning_endpoint_weak")
    val chat_reasoning_off_short: String get() = catalog().getValue("chat_reasoning_off_short")
    val chat_reasoning_toggle: String get() = catalog().getValue("chat_reasoning_toggle")
    val chat_reasoning_unknown_hint: String get() = catalog().getValue("chat_reasoning_unknown_hint")
    val chat_remote_connect_failed: String get() = catalog().getValue("chat_remote_connect_failed")
    val chat_remove_attachment: String get() = catalog().getValue("chat_remove_attachment")
    val chat_rename_session: String get() = catalog().getValue("chat_rename_session")
    val chat_retrying: String get() = catalog().getValue("chat_retrying")
    val chat_search_clear: String get() = catalog().getValue("chat_search_clear")
    val chat_search_hint: String get() = catalog().getValue("chat_search_hint")
    val chat_search_no_result: String get() = catalog().getValue("chat_search_no_result")
    val chat_select_workspace_first: String get() = catalog().getValue("chat_select_workspace_first")
    val chat_send: String get() = catalog().getValue("chat_send")
    val chat_session_name: String get() = catalog().getValue("chat_session_name")
    val chat_sessions_selected_count: String get() = catalog().getValue("chat_sessions_selected_count")
    val chat_settings: String get() = catalog().getValue("chat_settings")
    val chat_ssh_connected: String get() = catalog().getValue("chat_ssh_connected")
    val chat_ssh_connecting: String get() = catalog().getValue("chat_ssh_connecting")
    val chat_ssh_disconnected: String get() = catalog().getValue("chat_ssh_disconnected")
    val chat_ssh_failed: String get() = catalog().getValue("chat_ssh_failed")
    val chat_status_asking_user: String get() = catalog().getValue("chat_status_asking_user")
    val chat_status_browsing_web: String get() = catalog().getValue("chat_status_browsing_web")
    val chat_status_calling_tool: String get() = catalog().getValue("chat_status_calling_tool")
    val chat_status_editing_file: String get() = catalog().getValue("chat_status_editing_file")
    val chat_status_generating: String get() = catalog().getValue("chat_status_generating")
    val chat_status_generating_image: String get() = catalog().getValue("chat_status_generating_image")
    val chat_status_reading_file: String get() = catalog().getValue("chat_status_reading_file")
    val chat_status_reading_memory: String get() = catalog().getValue("chat_status_reading_memory")
    val chat_status_running_command: String get() = catalog().getValue("chat_status_running_command")
    val chat_status_searching_web: String get() = catalog().getValue("chat_status_searching_web")
    val chat_status_starting_subagent: String get() = catalog().getValue("chat_status_starting_subagent")
    val chat_status_thinking: String get() = catalog().getValue("chat_status_thinking")
    val chat_status_updating_todo: String get() = catalog().getValue("chat_status_updating_todo")
    val chat_status_viewing_image: String get() = catalog().getValue("chat_status_viewing_image")
    val chat_stop: String get() = catalog().getValue("chat_stop")
    val chat_take_photo: String get() = catalog().getValue("chat_take_photo")
    val chat_task_duration: String get() = catalog().getValue("chat_task_duration")
    val chat_thinking_done: String get() = catalog().getValue("chat_thinking_done")
    val chat_thinking_done_time: String get() = catalog().getValue("chat_thinking_done_time")
    val chat_thinking_process: String get() = catalog().getValue("chat_thinking_process")
    val chat_thinking_running_time: String get() = catalog().getValue("chat_thinking_running_time")
    val chat_tool_calls_count: String get() = catalog().getValue("chat_tool_calls_count")
    val chat_tool_failed: String get() = catalog().getValue("chat_tool_failed")
    val chat_tool_running: String get() = catalog().getValue("chat_tool_running")
    val chat_tool_succeeded: String get() = catalog().getValue("chat_tool_succeeded")
    val chat_turn_completed: String get() = catalog().getValue("chat_turn_completed")
    val chat_turn_completed_plain: String get() = catalog().getValue("chat_turn_completed_plain")
    val chat_turn_running: String get() = catalog().getValue("chat_turn_running")
    val chat_unpin_session: String get() = catalog().getValue("chat_unpin_session")
    val chat_unsupported_image_type: String get() = catalog().getValue("chat_unsupported_image_type")
    val chat_upload_failed: String get() = catalog().getValue("chat_upload_failed")
    val chat_upload_file: String get() = catalog().getValue("chat_upload_file")
    val chat_upload_image: String get() = catalog().getValue("chat_upload_image")
    val chat_uploaded_to: String get() = catalog().getValue("chat_uploaded_to")
    val chat_workspace_unavailable: String get() = catalog().getValue("chat_workspace_unavailable")
    val checkpoint_restore_code: String get() = catalog().getValue("checkpoint_restore_code")
    val checkpoint_restore_code_and_conversation: String get() = catalog().getValue("checkpoint_restore_code_and_conversation")
    val checkpoint_restore_code_and_conversation_desc: String get() = catalog().getValue("checkpoint_restore_code_and_conversation_desc")
    val checkpoint_restore_code_desc: String get() = catalog().getValue("checkpoint_restore_code_desc")
    val checkpoint_restore_conversation: String get() = catalog().getValue("checkpoint_restore_conversation")
    val checkpoint_restore_conversation_desc: String get() = catalog().getValue("checkpoint_restore_conversation_desc")
    val checkpoint_rewind_title: String get() = catalog().getValue("checkpoint_rewind_title")
    val checkpoint_skipped_multiple: String get() = catalog().getValue("checkpoint_skipped_multiple")
    val checkpoint_skipped_single: String get() = catalog().getValue("checkpoint_skipped_single")
    val checkpoint_target_prompt: String get() = catalog().getValue("checkpoint_target_prompt")
    val common_add: String get() = catalog().getValue("common_add")
    val common_ai_providers: String get() = catalog().getValue("common_ai_providers")
    val common_allow: String get() = catalog().getValue("common_allow")
    val common_back: String get() = catalog().getValue("common_back")
    val common_batch_delete: String get() = catalog().getValue("common_batch_delete")
    val common_batch_deleting: String get() = catalog().getValue("common_batch_deleting")
    val common_batch_disable: String get() = catalog().getValue("common_batch_disable")
    val common_batch_enable: String get() = catalog().getValue("common_batch_enable")
    val common_batch_reconnect: String get() = catalog().getValue("common_batch_reconnect")
    val common_cancel: String get() = catalog().getValue("common_cancel")
    val common_clear: String get() = catalog().getValue("common_clear")
    val common_close: String get() = catalog().getValue("common_close")
    val common_collapse: String get() = catalog().getValue("common_collapse")
    val common_collapse_action: String get() = catalog().getValue("common_collapse_action")
    val common_confirm: String get() = catalog().getValue("common_confirm")
    val common_copied_with_title: String get() = catalog().getValue("common_copied_with_title")
    val common_copy: String get() = catalog().getValue("common_copy")
    val common_copy_success: String get() = catalog().getValue("common_copy_success")
    val common_create: String get() = catalog().getValue("common_create")
    val common_cut: String get() = catalog().getValue("common_cut")
    val common_delete: String get() = catalog().getValue("common_delete")
    val common_deselect_all: String get() = catalog().getValue("common_deselect_all")
    val common_disabled: String get() = catalog().getValue("common_disabled")
    val common_done: String get() = catalog().getValue("common_done")
    val common_edit: String get() = catalog().getValue("common_edit")
    val common_enabled: String get() = catalog().getValue("common_enabled")
    val common_error: String get() = catalog().getValue("common_error")
    val common_error_unknown: String get() = catalog().getValue("common_error_unknown")
    val common_expand: String get() = catalog().getValue("common_expand")
    val common_file: String get() = catalog().getValue("common_file")
    val common_go_to_settings: String get() = catalog().getValue("common_go_to_settings")
    val common_got_it: String get() = catalog().getValue("common_got_it")
    val common_hide: String get() = catalog().getValue("common_hide")
    val common_image_load_failed: String get() = catalog().getValue("common_image_load_failed")
    val common_image_preview: String get() = catalog().getValue("common_image_preview")
    val common_label_with_api: String get() = catalog().getValue("common_label_with_api")
    val common_local: String get() = catalog().getValue("common_local")
    val common_model: String get() = catalog().getValue("common_model")
    val common_more: String get() = catalog().getValue("common_more")
    val common_name: String get() = catalog().getValue("common_name")
    val common_new_tab: String get() = catalog().getValue("common_new_tab")
    val common_next: String get() = catalog().getValue("common_next")
    val common_open_link_failed: String get() = catalog().getValue("common_open_link_failed")
    val common_other: String get() = catalog().getValue("common_other")
    val common_overwrite: String get() = catalog().getValue("common_overwrite")
    val common_paste: String get() = catalog().getValue("common_paste")
    val common_prev: String get() = catalog().getValue("common_prev")
    val common_rename: String get() = catalog().getValue("common_rename")
    val common_reset: String get() = catalog().getValue("common_reset")
    val common_retry: String get() = catalog().getValue("common_retry")
    val common_save: String get() = catalog().getValue("common_save")
    val common_scroll_to_bottom: String get() = catalog().getValue("common_scroll_to_bottom")
    val common_select: String get() = catalog().getValue("common_select")
    val common_select_all: String get() = catalog().getValue("common_select_all")
    val common_select_mode: String get() = catalog().getValue("common_select_mode")
    val common_selected_count: String get() = catalog().getValue("common_selected_count")
    val common_show: String get() = catalog().getValue("common_show")
    val common_switch: String get() = catalog().getValue("common_switch")
    val common_tool: String get() = catalog().getValue("common_tool")
    val common_tool_missing_argument: String get() = catalog().getValue("common_tool_missing_argument")
    val common_username: String get() = catalog().getValue("common_username")
    val common_workspace: String get() = catalog().getValue("common_workspace")
    val compaction_no_models: String get() = catalog().getValue("compaction_no_models")
    val container_add_arg: String get() = catalog().getValue("container_add_arg")
    val container_add_binding: String get() = catalog().getValue("container_add_binding")
    val container_add_env_var: String get() = catalog().getValue("container_add_env_var")
    val container_add_image: String get() = catalog().getValue("container_add_image")
    val container_announcement_got_it: String get() = catalog().getValue("container_announcement_got_it")
    val container_announcement_title: String get() = catalog().getValue("container_announcement_title")
    val container_arg_value: String get() = catalog().getValue("container_arg_value")
    val container_builtin_badge: String get() = catalog().getValue("container_builtin_badge")
    val container_builtin_restorable: String get() = catalog().getValue("container_builtin_restorable")
    val container_channel_deleted: String get() = catalog().getValue("container_channel_deleted")
    val container_custom_badge: String get() = catalog().getValue("container_custom_badge")
    val container_custom_image: String get() = catalog().getValue("container_custom_image")
    val container_default_badge: String get() = catalog().getValue("container_default_badge")
    val container_default_desc: String get() = catalog().getValue("container_default_desc")
    val container_delete_config: String get() = catalog().getValue("container_delete_config")
    val container_delete_confirm: String get() = catalog().getValue("container_delete_confirm")
    val container_download_architecture: String get() = catalog().getValue("container_download_architecture")
    val container_download_batch_delete_confirm: String get() = catalog().getValue("container_download_batch_delete_confirm")
    val container_download_batch_delete_title: String get() = catalog().getValue("container_download_batch_delete_title")
    val container_download_cancel: String get() = catalog().getValue("container_download_cancel")
    val container_download_checksum_failed: String get() = catalog().getValue("container_download_checksum_failed")
    val container_download_current_source: String get() = catalog().getValue("container_download_current_source")
    val container_download_current_source_title: String get() = catalog().getValue("container_download_current_source_title")
    val container_download_delete_confirm: String get() = catalog().getValue("container_download_delete_confirm")
    val container_download_delete_title: String get() = catalog().getValue("container_download_delete_title")
    val container_download_done: String get() = catalog().getValue("container_download_done")
    val container_download_eol_ended: String get() = catalog().getValue("container_download_eol_ended")
    val container_download_eol_extended: String get() = catalog().getValue("container_download_eol_extended")
    val container_download_eol_extended_generic: String get() = catalog().getValue("container_download_eol_extended_generic")
    val container_download_eol_fully_ends: String get() = catalog().getValue("container_download_eol_fully_ends")
    val container_download_eol_soon: String get() = catalog().getValue("container_download_eol_soon")
    val container_download_eol_support_ends: String get() = catalog().getValue("container_download_eol_support_ends")
    val container_download_failed: String get() = catalog().getValue("container_download_failed")
    val container_download_image: String get() = catalog().getValue("container_download_image")
    val container_download_import: String get() = catalog().getValue("container_download_import")
    val container_download_imported_toast: String get() = catalog().getValue("container_download_imported_toast")
    val container_download_list_empty: String get() = catalog().getValue("container_download_list_empty")
    val container_download_newer_available: String get() = catalog().getValue("container_download_newer_available")
    val container_download_redownload: String get() = catalog().getValue("container_download_redownload")
    val container_download_size: String get() = catalog().getValue("container_download_size")
    val container_download_source_unavailable: String get() = catalog().getValue("container_download_source_unavailable")
    val container_download_start: String get() = catalog().getValue("container_download_start")
    val container_edit_image: String get() = catalog().getValue("container_edit_image")
    val container_empty: String get() = catalog().getValue("container_empty")
    val container_empty_desc: String get() = catalog().getValue("container_empty_desc")
    val container_env_name: String get() = catalog().getValue("container_env_name")
    val container_env_value: String get() = catalog().getValue("container_env_value")
    val container_env_vars: String get() = catalog().getValue("container_env_vars")
    val container_extra_bindings: String get() = catalog().getValue("container_extra_bindings")
    val container_extra_proot_args: String get() = catalog().getValue("container_extra_proot_args")
    val container_file_selected: String get() = catalog().getValue("container_file_selected")
    val container_importing: String get() = catalog().getValue("container_importing")
    val container_local_image: String get() = catalog().getValue("container_local_image")
    val container_mount_container: String get() = catalog().getValue("container_mount_container")
    val container_mount_local: String get() = catalog().getValue("container_mount_local")
    val container_no_args: String get() = catalog().getValue("container_no_args")
    val container_no_bindings: String get() = catalog().getValue("container_no_bindings")
    val container_no_env_vars: String get() = catalog().getValue("container_no_env_vars")
    val container_no_sftp_channel: String get() = catalog().getValue("container_no_sftp_channel")
    val container_not_ready_hint: String get() = catalog().getValue("container_not_ready_hint")
    val container_remote_ssh: String get() = catalog().getValue("container_remote_ssh")
    val container_remote_workspace_path: String get() = catalog().getValue("container_remote_workspace_path")
    val container_reset: String get() = catalog().getValue("container_reset")
    val container_reset_confirm: String get() = catalog().getValue("container_reset_confirm")
    val container_reset_title: String get() = catalog().getValue("container_reset_title")
    val container_resetting_message: String get() = catalog().getValue("container_resetting_message")
    val container_resetting_title: String get() = catalog().getValue("container_resetting_title")
    val container_restore_builtin: String get() = catalog().getValue("container_restore_builtin")
    val container_rootfs_will_be_cleared: String get() = catalog().getValue("container_rootfs_will_be_cleared")
    val container_select_image_file: String get() = catalog().getValue("container_select_image_file")
    val container_select_ssh_channel: String get() = catalog().getValue("container_select_ssh_channel")
    val container_set_as_default: String get() = catalog().getValue("container_set_as_default")
    val container_shell_desc: String get() = catalog().getValue("container_shell_desc")
    val container_shell_path: String get() = catalog().getValue("container_shell_path")
    val container_sort_long_press: String get() = catalog().getValue("container_sort_long_press")
    val container_ssh_badge: String get() = catalog().getValue("container_ssh_badge")
    val container_ssh_channel: String get() = catalog().getValue("container_ssh_channel")
    val container_switch_confirm: String get() = catalog().getValue("container_switch_confirm")
    val container_switch_image: String get() = catalog().getValue("container_switch_image")
    val crash_banner_title: String get() = catalog().getValue("crash_banner_title")
    val crash_copied: String get() = catalog().getValue("crash_copied")
    val crash_copy: String get() = catalog().getValue("crash_copy")
    val crash_exit: String get() = catalog().getValue("crash_exit")
    val crash_field_device: String get() = catalog().getValue("crash_field_device")
    val crash_field_memory: String get() = catalog().getValue("crash_field_memory")
    val crash_field_screen: String get() = catalog().getValue("crash_field_screen")
    val crash_field_thread: String get() = catalog().getValue("crash_field_thread")
    val crash_field_time: String get() = catalog().getValue("crash_field_time")
    val crash_field_version: String get() = catalog().getValue("crash_field_version")
    val crash_field_workspace_mode: String get() = catalog().getValue("crash_field_workspace_mode")
    val crash_log_hint: String get() = catalog().getValue("crash_log_hint")
    val crash_message: String get() = catalog().getValue("crash_message")
    val crash_mode_local: String get() = catalog().getValue("crash_mode_local")
    val crash_mode_remote: String get() = catalog().getValue("crash_mode_remote")
    val crash_mode_unknown: String get() = catalog().getValue("crash_mode_unknown")
    val crash_report_device: String get() = catalog().getValue("crash_report_device")
    val crash_report_log: String get() = catalog().getValue("crash_report_log")
    val crash_report_memory: String get() = catalog().getValue("crash_report_memory")
    val crash_report_mode: String get() = catalog().getValue("crash_report_mode")
    val crash_report_screen: String get() = catalog().getValue("crash_report_screen")
    val crash_report_thread: String get() = catalog().getValue("crash_report_thread")
    val crash_report_time: String get() = catalog().getValue("crash_report_time")
    val crash_report_title: String get() = catalog().getValue("crash_report_title")
    val crash_report_version: String get() = catalog().getValue("crash_report_version")
    val crash_restart: String get() = catalog().getValue("crash_restart")
    val crash_screen_chat: String get() = catalog().getValue("crash_screen_chat")
    val crash_screen_git: String get() = catalog().getValue("crash_screen_git")
    val crash_screen_settings: String get() = catalog().getValue("crash_screen_settings")
    val crash_screen_terminal: String get() = catalog().getValue("crash_screen_terminal")
    val crash_screen_unknown: String get() = catalog().getValue("crash_screen_unknown")
    val crash_section_environment: String get() = catalog().getValue("crash_section_environment")
    val crash_section_stack: String get() = catalog().getValue("crash_section_stack")
    val crash_title: String get() = catalog().getValue("crash_title")
    val credential_add: String get() = catalog().getValue("credential_add")
    val credential_add_github_desc: String get() = catalog().getValue("credential_add_github_desc")
    val credential_add_github_title: String get() = catalog().getValue("credential_add_github_title")
    val credential_add_manual: String get() = catalog().getValue("credential_add_manual")
    val credential_add_manual_desc: String get() = catalog().getValue("credential_add_manual_desc")
    val credential_add_manual_subtitle: String get() = catalog().getValue("credential_add_manual_subtitle")
    val credential_add_manual_title: String get() = catalog().getValue("credential_add_manual_title")
    val credential_add_recommend: String get() = catalog().getValue("credential_add_recommend")
    val credential_add_sheet_subtitle: String get() = catalog().getValue("credential_add_sheet_subtitle")
    val credential_add_sheet_title: String get() = catalog().getValue("credential_add_sheet_title")
    val credential_auto_inject_enable: String get() = catalog().getValue("credential_auto_inject_enable")
    val credential_auto_inject_hint: String get() = catalog().getValue("credential_auto_inject_hint")
    val credential_auto_inject_prompt_body: String get() = catalog().getValue("credential_auto_inject_prompt_body")
    val credential_auto_inject_prompt_title: String get() = catalog().getValue("credential_auto_inject_prompt_title")
    val credential_auto_inject_title: String get() = catalog().getValue("credential_auto_inject_title")
    val credential_device_authorize_and_open: String get() = catalog().getValue("credential_device_authorize_and_open")
    val credential_device_bad_client: String get() = catalog().getValue("credential_device_bad_client")
    val credential_device_card_label: String get() = catalog().getValue("credential_device_card_label")
    val credential_device_code_copied: String get() = catalog().getValue("credential_device_code_copied")
    val credential_device_code_copied_opening: String get() = catalog().getValue("credential_device_code_copied_opening")
    val credential_device_copy_code: String get() = catalog().getValue("credential_device_copy_code")
    val credential_device_denied: String get() = catalog().getValue("credential_device_denied")
    val credential_device_expired: String get() = catalog().getValue("credential_device_expired")
    val credential_device_failed: String get() = catalog().getValue("credential_device_failed")
    val credential_device_failed_title: String get() = catalog().getValue("credential_device_failed_title")
    val credential_device_flow_disabled: String get() = catalog().getValue("credential_device_flow_disabled")
    val credential_device_install_later: String get() = catalog().getValue("credential_device_install_later")
    val credential_device_install_now: String get() = catalog().getValue("credential_device_install_now")
    val credential_device_install_required: String get() = catalog().getValue("credential_device_install_required")
    val credential_device_instruction: String get() = catalog().getValue("credential_device_instruction")
    val credential_device_open_browser: String get() = catalog().getValue("credential_device_open_browser")
    val credential_device_requesting: String get() = catalog().getValue("credential_device_requesting")
    val credential_device_step_1: String get() = catalog().getValue("credential_device_step_1")
    val credential_device_step_2: String get() = catalog().getValue("credential_device_step_2")
    val credential_device_subtitle: String get() = catalog().getValue("credential_device_subtitle")
    val credential_device_success: String get() = catalog().getValue("credential_device_success")
    val credential_device_success_title: String get() = catalog().getValue("credential_device_success_title")
    val credential_device_tap_to_copy: String get() = catalog().getValue("credential_device_tap_to_copy")
    val credential_device_title: String get() = catalog().getValue("credential_device_title")
    val credential_device_unknown_error: String get() = catalog().getValue("credential_device_unknown_error")
    val credential_device_waiting: String get() = catalog().getValue("credential_device_waiting")
    val credential_device_waiting_desc: String get() = catalog().getValue("credential_device_waiting_desc")
    val credential_edit: String get() = catalog().getValue("credential_edit")
    val credential_empty_desc: String get() = catalog().getValue("credential_empty_desc")
    val credential_empty_hint: String get() = catalog().getValue("credential_empty_hint")
    val credential_empty_title: String get() = catalog().getValue("credential_empty_title")
    val credential_host: String get() = catalog().getValue("credential_host")
    val credential_prompt_desc: String get() = catalog().getValue("credential_prompt_desc")
    val credential_prompt_title: String get() = catalog().getValue("credential_prompt_title")
    val credential_quick_login: String get() = catalog().getValue("credential_quick_login")
    val credential_quick_login_desc: String get() = catalog().getValue("credential_quick_login_desc")
    val credential_quick_login_github_desc: String get() = catalog().getValue("credential_quick_login_github_desc")
    val credential_quick_login_title: String get() = catalog().getValue("credential_quick_login_title")
    val credential_remote_host: String get() = catalog().getValue("credential_remote_host")
    val credential_save_and_retry: String get() = catalog().getValue("credential_save_and_retry")
    val credential_toast_deleted: String get() = catalog().getValue("credential_toast_deleted")
    val credential_toast_identity_saved: String get() = catalog().getValue("credential_toast_identity_saved")
    val credential_toast_save_failed: String get() = catalog().getValue("credential_toast_save_failed")
    val credential_toast_saved: String get() = catalog().getValue("credential_toast_saved")
    val credential_token: String get() = catalog().getValue("credential_token")
    val dashboard_exec_failed: String get() = catalog().getValue("dashboard_exec_failed")
    val dashboard_fetch_failed_retry: String get() = catalog().getValue("dashboard_fetch_failed_retry")
    val dashboard_fetching: String get() = catalog().getValue("dashboard_fetching")
    val dashboard_query_failed: String get() = catalog().getValue("dashboard_query_failed")
    val dashboard_tap_to_query: String get() = catalog().getValue("dashboard_tap_to_query")
    val diff_binary_file: String get() = catalog().getValue("diff_binary_file")
    val diff_file_too_large: String get() = catalog().getValue("diff_file_too_large")
    val diff_file_too_large_desc: String get() = catalog().getValue("diff_file_too_large_desc")
    val diff_no_changes: String get() = catalog().getValue("diff_no_changes")
    val drawer_collapse_subagents: String get() = catalog().getValue("drawer_collapse_subagents")
    val drawer_expand_subagents: String get() = catalog().getValue("drawer_expand_subagents")
    val drawer_tab_files: String get() = catalog().getValue("drawer_tab_files")
    val editor_binary_unsupported: String get() = catalog().getValue("editor_binary_unsupported")
    val editor_cursor_left: String get() = catalog().getValue("editor_cursor_left")
    val editor_cursor_right: String get() = catalog().getValue("editor_cursor_right")
    val editor_discard: String get() = catalog().getValue("editor_discard")
    val editor_dont_save: String get() = catalog().getValue("editor_dont_save")
    val editor_file_too_large: String get() = catalog().getValue("editor_file_too_large")
    val editor_font_size: String get() = catalog().getValue("editor_font_size")
    val editor_font_size_value: String get() = catalog().getValue("editor_font_size_value")
    val editor_indent: String get() = catalog().getValue("editor_indent")
    val editor_load_failed: String get() = catalog().getValue("editor_load_failed")
    val editor_md_preview: String get() = catalog().getValue("editor_md_preview")
    val editor_md_show_source: String get() = catalog().getValue("editor_md_show_source")
    val editor_redo: String get() = catalog().getValue("editor_redo")
    val editor_save: String get() = catalog().getValue("editor_save")
    val editor_save_and_exit: String get() = catalog().getValue("editor_save_and_exit")
    val editor_save_and_switch: String get() = catalog().getValue("editor_save_and_switch")
    val editor_save_conflict_cancel: String get() = catalog().getValue("editor_save_conflict_cancel")
    val editor_save_conflict_diff: String get() = catalog().getValue("editor_save_conflict_diff")
    val editor_save_conflict_discard: String get() = catalog().getValue("editor_save_conflict_discard")
    val editor_save_conflict_discarded: String get() = catalog().getValue("editor_save_conflict_discarded")
    val editor_save_conflict_message: String get() = catalog().getValue("editor_save_conflict_message")
    val editor_save_conflict_overwrite: String get() = catalog().getValue("editor_save_conflict_overwrite")
    val editor_save_conflict_title: String get() = catalog().getValue("editor_save_conflict_title")
    val editor_save_failed: String get() = catalog().getValue("editor_save_failed")
    val editor_save_success: String get() = catalog().getValue("editor_save_success")
    val editor_settings: String get() = catalog().getValue("editor_settings")
    val editor_show_indent_guide: String get() = catalog().getValue("editor_show_indent_guide")
    val editor_show_whitespace: String get() = catalog().getValue("editor_show_whitespace")
    val editor_show_wrap_arrow: String get() = catalog().getValue("editor_show_wrap_arrow")
    val editor_undo: String get() = catalog().getValue("editor_undo")
    val editor_unsaved_message: String get() = catalog().getValue("editor_unsaved_message")
    val editor_unsaved_title: String get() = catalog().getValue("editor_unsaved_title")
    val editor_word_wrap: String get() = catalog().getValue("editor_word_wrap")
    val file_browser_clipboard_copy: String get() = catalog().getValue("file_browser_clipboard_copy")
    val file_browser_clipboard_cut: String get() = catalog().getValue("file_browser_clipboard_cut")
    val file_browser_collapse: String get() = catalog().getValue("file_browser_collapse")
    val file_browser_create_failed: String get() = catalog().getValue("file_browser_create_failed")
    val file_browser_delete_failed: String get() = catalog().getValue("file_browser_delete_failed")
    val file_browser_delete_file_confirm: String get() = catalog().getValue("file_browser_delete_file_confirm")
    val file_browser_delete_folder_confirm: String get() = catalog().getValue("file_browser_delete_folder_confirm")
    val file_browser_empty: String get() = catalog().getValue("file_browser_empty")
    val file_browser_error: String get() = catalog().getValue("file_browser_error")
    val file_browser_expand: String get() = catalog().getValue("file_browser_expand")
    val file_browser_name_label: String get() = catalog().getValue("file_browser_name_label")
    val file_browser_new_file: String get() = catalog().getValue("file_browser_new_file")
    val file_browser_new_folder: String get() = catalog().getValue("file_browser_new_folder")
    val file_browser_paste_conflict_message: String get() = catalog().getValue("file_browser_paste_conflict_message")
    val file_browser_paste_conflict_title: String get() = catalog().getValue("file_browser_paste_conflict_title")
    val file_browser_paste_here: String get() = catalog().getValue("file_browser_paste_here")
    val file_browser_refresh: String get() = catalog().getValue("file_browser_refresh")
    val file_browser_set_as_workspace: String get() = catalog().getValue("file_browser_set_as_workspace")
    val chat_collapse_sidebar: String get() = catalog().getValue("chat_collapse_sidebar")
    val prompts_help_body: String get() = catalog().getValue("prompts_help_body")
    val window_minimize: String get() = catalog().getValue("window_minimize")
    val window_maximize: String get() = catalog().getValue("window_maximize")
    val window_close: String get() = catalog().getValue("window_close")
    val file_browser_rename_failed: String get() = catalog().getValue("file_browser_rename_failed")
    val file_browser_up: String get() = catalog().getValue("file_browser_up")
    val ftp_address_copied: String get() = catalog().getValue("ftp_address_copied")
    val ftp_allow_anonymous: String get() = catalog().getValue("ftp_allow_anonymous")
    val ftp_anonymous_confirm_message: String get() = catalog().getValue("ftp_anonymous_confirm_message")
    val ftp_anonymous_confirm_title: String get() = catalog().getValue("ftp_anonymous_confirm_title")
    val ftp_anonymous_desc: String get() = catalog().getValue("ftp_anonymous_desc")
    val ftp_auto_start: String get() = catalog().getValue("ftp_auto_start")
    val ftp_auto_start_desc: String get() = catalog().getValue("ftp_auto_start_desc")
    val ftp_config_saved: String get() = catalog().getValue("ftp_config_saved")
    val ftp_config_title: String get() = catalog().getValue("ftp_config_title")
    val ftp_copy_address: String get() = catalog().getValue("ftp_copy_address")
    val ftp_error: String get() = catalog().getValue("ftp_error")
    val ftp_listen_port: String get() = catalog().getValue("ftp_listen_port")
    val ftp_login_password: String get() = catalog().getValue("ftp_login_password")
    val ftp_login_username: String get() = catalog().getValue("ftp_login_username")
    val ftp_not_running: String get() = catalog().getValue("ftp_not_running")
    val ftp_password_required: String get() = catalog().getValue("ftp_password_required")
    val ftp_reserved_username: String get() = catalog().getValue("ftp_reserved_username")
    val ftp_running: String get() = catalog().getValue("ftp_running")
    val ftp_save_config: String get() = catalog().getValue("ftp_save_config")
    val ftp_usage_item_1: String get() = catalog().getValue("ftp_usage_item_1")
    val ftp_usage_item_2: String get() = catalog().getValue("ftp_usage_item_2")
    val ftp_usage_item_3: String get() = catalog().getValue("ftp_usage_item_3")
    val ftp_usage_title: String get() = catalog().getValue("ftp_usage_title")
    val ftp_user_cache_cleanup_failed: String get() = catalog().getValue("ftp_user_cache_cleanup_failed")
    val git_abort_merge_confirm: String get() = catalog().getValue("git_abort_merge_confirm")
    val git_action_abort_merge: String get() = catalog().getValue("git_action_abort_merge")
    val git_action_clone: String get() = catalog().getValue("git_action_clone")
    val git_action_commit: String get() = catalog().getValue("git_action_commit")
    val git_action_copy_path: String get() = catalog().getValue("git_action_copy_path")
    val git_action_create_branch: String get() = catalog().getValue("git_action_create_branch")
    val git_action_create_tag: String get() = catalog().getValue("git_action_create_tag")
    val git_action_delete_branch: String get() = catalog().getValue("git_action_delete_branch")
    val git_action_delete_dir: String get() = catalog().getValue("git_action_delete_dir")
    val git_action_delete_file: String get() = catalog().getValue("git_action_delete_file")
    val git_action_delete_remote_branch: String get() = catalog().getValue("git_action_delete_remote_branch")
    val git_action_delete_tag: String get() = catalog().getValue("git_action_delete_tag")
    val git_action_init: String get() = catalog().getValue("git_action_init")
    val git_action_merge: String get() = catalog().getValue("git_action_merge")
    val git_action_rename_branch: String get() = catalog().getValue("git_action_rename_branch")
    val git_action_restore_file: String get() = catalog().getValue("git_action_restore_file")
    val git_action_revert: String get() = catalog().getValue("git_action_revert")
    val git_action_revert_all: String get() = catalog().getValue("git_action_revert_all")
    val git_action_stage_all: String get() = catalog().getValue("git_action_stage_all")
    val git_action_stash: String get() = catalog().getValue("git_action_stash")
    val git_action_unstage_all: String get() = catalog().getValue("git_action_unstage_all")
    val git_action_view_diff: String get() = catalog().getValue("git_action_view_diff")
    val git_add_to_gitignore: String get() = catalog().getValue("git_add_to_gitignore")
    val git_ahead_count: String get() = catalog().getValue("git_ahead_count")
    val git_base_branch: String get() = catalog().getValue("git_base_branch")
    val git_behind_count: String get() = catalog().getValue("git_behind_count")
    val git_branch_name: String get() = catalog().getValue("git_branch_name")
    val git_branch_synced: String get() = catalog().getValue("git_branch_synced")
    val git_checked_out: String get() = catalog().getValue("git_checked_out")
    val git_clean: String get() = catalog().getValue("git_clean")
    val git_clone_desc: String get() = catalog().getValue("git_clone_desc")
    val git_clone_dialog_title: String get() = catalog().getValue("git_clone_dialog_title")
    val git_clone_repo: String get() = catalog().getValue("git_clone_repo")
    val git_clone_url_hint: String get() = catalog().getValue("git_clone_url_hint")
    val git_clone_url_label: String get() = catalog().getValue("git_clone_url_label")
    val git_cloning_hint: String get() = catalog().getValue("git_cloning_hint")
    val git_commit_ai_generate: String get() = catalog().getValue("git_commit_ai_generate")
    val git_commit_amend: String get() = catalog().getValue("git_commit_amend")
    val git_commit_changes: String get() = catalog().getValue("git_commit_changes")
    val git_commit_count: String get() = catalog().getValue("git_commit_count")
    val git_commit_dialog_title: String get() = catalog().getValue("git_commit_dialog_title")
    val git_commit_generating: String get() = catalog().getValue("git_commit_generating")
    val git_commit_message: String get() = catalog().getValue("git_commit_message")
    val git_commit_needs_identity: String get() = catalog().getValue("git_commit_needs_identity")
    val git_computing_diff: String get() = catalog().getValue("git_computing_diff")
    val git_conflicted_count: String get() = catalog().getValue("git_conflicted_count")
    val git_copy_full_hash: String get() = catalog().getValue("git_copy_full_hash")
    val git_copy_short_hash: String get() = catalog().getValue("git_copy_short_hash")
    val git_create_and_switch: String get() = catalog().getValue("git_create_and_switch")
    val git_create_branch_from_commit: String get() = catalog().getValue("git_create_branch_from_commit")
    val git_create_tag_at_commit: String get() = catalog().getValue("git_create_tag_at_commit")
    val git_create_tag_desc: String get() = catalog().getValue("git_create_tag_desc")
    val git_create_tag_title: String get() = catalog().getValue("git_create_tag_title")
    val git_create_tracking_branch: String get() = catalog().getValue("git_create_tracking_branch")
    val git_credentials_and_identity: String get() = catalog().getValue("git_credentials_and_identity")
    val git_credentials_count: String get() = catalog().getValue("git_credentials_count")
    val git_current_branch: String get() = catalog().getValue("git_current_branch")
    val git_delete_branch: String get() = catalog().getValue("git_delete_branch")
    val git_delete_local_branch_confirm: String get() = catalog().getValue("git_delete_local_branch_confirm")
    val git_delete_remote_branch: String get() = catalog().getValue("git_delete_remote_branch")
    val git_delete_remote_branch_confirm: String get() = catalog().getValue("git_delete_remote_branch_confirm")
    val git_delete_tag: String get() = catalog().getValue("git_delete_tag")
    val git_delete_tag_confirm: String get() = catalog().getValue("git_delete_tag_confirm")
    val git_delete_untracked_confirm: String get() = catalog().getValue("git_delete_untracked_confirm")
    val git_detached_head_label: String get() = catalog().getValue("git_detached_head_label")
    val git_detached_head_warning: String get() = catalog().getValue("git_detached_head_warning")
    val git_expanded: String get() = catalog().getValue("git_expanded")
    val git_files_changed: String get() = catalog().getValue("git_files_changed")
    val git_has_changes: String get() = catalog().getValue("git_has_changes")
    val git_identity_desc: String get() = catalog().getValue("git_identity_desc")
    val git_identity_title: String get() = catalog().getValue("git_identity_title")
    val git_ignore_dir: String get() = catalog().getValue("git_ignore_dir")
    val git_ignore_extension: String get() = catalog().getValue("git_ignore_extension")
    val git_ignore_file: String get() = catalog().getValue("git_ignore_file")
    val git_init_desc_primary: String get() = catalog().getValue("git_init_desc_primary")
    val git_init_desc_secondary: String get() = catalog().getValue("git_init_desc_secondary")
    val git_init_repo: String get() = catalog().getValue("git_init_repo")
    val git_latest_commit: String get() = catalog().getValue("git_latest_commit")
    val git_latest_date: String get() = catalog().getValue("git_latest_date")
    val git_load_more_commits: String get() = catalog().getValue("git_load_more_commits")
    val git_loading_branches: String get() = catalog().getValue("git_loading_branches")
    val git_loading_files: String get() = catalog().getValue("git_loading_files")
    val git_merge_commit: String get() = catalog().getValue("git_merge_commit")
    val git_merge_confirm: String get() = catalog().getValue("git_merge_confirm")
    val git_merge_into: String get() = catalog().getValue("git_merge_into")
    val git_merge_no_ff: String get() = catalog().getValue("git_merge_no_ff")
    val git_merge_pending_hint: String get() = catalog().getValue("git_merge_pending_hint")
    val git_merge_pending_title: String get() = catalog().getValue("git_merge_pending_title")
    val git_modified_count: String get() = catalog().getValue("git_modified_count")
    val git_modified_label: String get() = catalog().getValue("git_modified_label")
    val git_new: String get() = catalog().getValue("git_new")
    val git_new_branch: String get() = catalog().getValue("git_new_branch")
    val git_new_branch_name: String get() = catalog().getValue("git_new_branch_name")
    val git_no_branch: String get() = catalog().getValue("git_no_branch")
    val git_no_branches: String get() = catalog().getValue("git_no_branches")
    val git_no_checked_out_branch: String get() = catalog().getValue("git_no_checked_out_branch")
    val git_no_commits: String get() = catalog().getValue("git_no_commits")
    val git_no_files_changed: String get() = catalog().getValue("git_no_files_changed")
    val git_no_identity: String get() = catalog().getValue("git_no_identity")
    val git_no_more_commits: String get() = catalog().getValue("git_no_more_commits")
    val git_no_upstream: String get() = catalog().getValue("git_no_upstream")
    val git_not_a_repo: String get() = catalog().getValue("git_not_a_repo")
    val git_open_in_editor: String get() = catalog().getValue("git_open_in_editor")
    val git_pick_existing_repo: String get() = catalog().getValue("git_pick_existing_repo")
    val git_pick_repo_empty: String get() = catalog().getValue("git_pick_repo_empty")
    val git_pull: String get() = catalog().getValue("git_pull")
    val git_pull_continue: String get() = catalog().getValue("git_pull_continue")
    val git_pull_dirty_confirm: String get() = catalog().getValue("git_pull_dirty_confirm")
    val git_push: String get() = catalog().getValue("git_push")
    val git_push_keep_master: String get() = catalog().getValue("git_push_keep_master")
    val git_push_master_confirm_desc: String get() = catalog().getValue("git_push_master_confirm_desc")
    val git_push_master_confirm_title: String get() = catalog().getValue("git_push_master_confirm_title")
    val git_push_rename_to_main: String get() = catalog().getValue("git_push_rename_to_main")
    val git_recent_commit: String get() = catalog().getValue("git_recent_commit")
    val git_refresh: String get() = catalog().getValue("git_refresh")
    val git_remote: String get() = catalog().getValue("git_remote")
    val git_remote_url: String get() = catalog().getValue("git_remote_url")
    val git_rename_branch: String get() = catalog().getValue("git_rename_branch")
    val git_rename_to: String get() = catalog().getValue("git_rename_to")
    val git_repo_not_repo: String get() = catalog().getValue("git_repo_not_repo")
    val git_repo_selector_title: String get() = catalog().getValue("git_repo_selector_title")
    val git_repo_url_title: String get() = catalog().getValue("git_repo_url_title")
    val git_reset_confirm: String get() = catalog().getValue("git_reset_confirm")
    val git_reset_mixed: String get() = catalog().getValue("git_reset_mixed")
    val git_reset_soft: String get() = catalog().getValue("git_reset_soft")
    val git_reset_to_commit: String get() = catalog().getValue("git_reset_to_commit")
    val git_restore_file_confirm: String get() = catalog().getValue("git_restore_file_confirm")
    val git_revert_all_confirm: String get() = catalog().getValue("git_revert_all_confirm")
    val git_revert_confirm_head: String get() = catalog().getValue("git_revert_confirm_head")
    val git_revert_confirm_worktree: String get() = catalog().getValue("git_revert_confirm_worktree")
    val git_search_commits_hint: String get() = catalog().getValue("git_search_commits_hint")
    val git_stage: String get() = catalog().getValue("git_stage")
    val git_stage_all: String get() = catalog().getValue("git_stage_all")
    val git_staged_count: String get() = catalog().getValue("git_staged_count")
    val git_staged_label: String get() = catalog().getValue("git_staged_label")
    val git_stash_apply: String get() = catalog().getValue("git_stash_apply")
    val git_stash_clear: String get() = catalog().getValue("git_stash_clear")
    val git_stash_clear_confirm: String get() = catalog().getValue("git_stash_clear_confirm")
    val git_stash_count: String get() = catalog().getValue("git_stash_count")
    val git_stash_drop: String get() = catalog().getValue("git_stash_drop")
    val git_stash_drop_confirm: String get() = catalog().getValue("git_stash_drop_confirm")
    val git_stash_empty: String get() = catalog().getValue("git_stash_empty")
    val git_stash_include_untracked: String get() = catalog().getValue("git_stash_include_untracked")
    val git_stash_message_hint: String get() = catalog().getValue("git_stash_message_hint")
    val git_stash_pop: String get() = catalog().getValue("git_stash_pop")
    val git_stash_title: String get() = catalog().getValue("git_stash_title")
    val git_status_clean: String get() = catalog().getValue("git_status_clean")
    val git_switch_branch: String get() = catalog().getValue("git_switch_branch")
    val git_switch_confirm: String get() = catalog().getValue("git_switch_confirm")
    val git_switch_repo: String get() = catalog().getValue("git_switch_repo")
    val git_tab_branches: String get() = catalog().getValue("git_tab_branches")
    val git_tab_commits: String get() = catalog().getValue("git_tab_commits")
    val git_tab_credentials: String get() = catalog().getValue("git_tab_credentials")
    val git_tab_identity: String get() = catalog().getValue("git_tab_identity")
    val git_tab_stash: String get() = catalog().getValue("git_tab_stash")
    val git_tab_status: String get() = catalog().getValue("git_tab_status")
    val git_tag_name: String get() = catalog().getValue("git_tag_name")
    val git_tags: String get() = catalog().getValue("git_tags")
    val git_title: String get() = catalog().getValue("git_title")
    val git_toast_action_failed: String get() = catalog().getValue("git_toast_action_failed")
    val git_toast_action_refresh_failed: String get() = catalog().getValue("git_toast_action_refresh_failed")
    val git_toast_action_success: String get() = catalog().getValue("git_toast_action_success")
    val git_toast_added_to_gitignore: String get() = catalog().getValue("git_toast_added_to_gitignore")
    val git_toast_checkout_failed: String get() = catalog().getValue("git_toast_checkout_failed")
    val git_toast_checkout_success: String get() = catalog().getValue("git_toast_checkout_success")
    val git_toast_diff_failed: String get() = catalog().getValue("git_toast_diff_failed")
    val git_toast_generate_commit_failed: String get() = catalog().getValue("git_toast_generate_commit_failed")
    val git_toast_load_branches_failed: String get() = catalog().getValue("git_toast_load_branches_failed")
    val git_toast_load_more_failed: String get() = catalog().getValue("git_toast_load_more_failed")
    val git_toast_no_diff_for_commit: String get() = catalog().getValue("git_toast_no_diff_for_commit")
    val git_toast_no_remote_pull: String get() = catalog().getValue("git_toast_no_remote_pull")
    val git_toast_no_remote_push: String get() = catalog().getValue("git_toast_no_remote_push")
    val git_toast_not_repo: String get() = catalog().getValue("git_toast_not_repo")
    val git_toast_path_copied: String get() = catalog().getValue("git_toast_path_copied")
    val git_toast_refresh_failed: String get() = catalog().getValue("git_toast_refresh_failed")
    val git_toast_switch_repo_failed: String get() = catalog().getValue("git_toast_switch_repo_failed")
    val git_tracking_branch: String get() = catalog().getValue("git_tracking_branch")
    val git_unstage: String get() = catalog().getValue("git_unstage")
    val git_unstage_all_confirm: String get() = catalog().getValue("git_unstage_all_confirm")
    val git_untracked_count: String get() = catalog().getValue("git_untracked_count")
    val git_untracked_label: String get() = catalog().getValue("git_untracked_label")
    val image_gen_no_models: String get() = catalog().getValue("image_gen_no_models")
    val language_follow_system: String get() = catalog().getValue("language_follow_system")
    val log_clear_filter: String get() = catalog().getValue("log_clear_filter")
    val log_copied: String get() = catalog().getValue("log_copied")
    val log_copy: String get() = catalog().getValue("log_copy")
    val log_copy_line: String get() = catalog().getValue("log_copy_line")
    val log_copy_menu_title: String get() = catalog().getValue("log_copy_menu_title")
    val log_copy_message: String get() = catalog().getValue("log_copy_message")
    val log_copy_with_stack: String get() = catalog().getValue("log_copy_with_stack")
    val log_file_location: String get() = catalog().getValue("log_file_location")
    val log_files: String get() = catalog().getValue("log_files")
    val log_follow_latest: String get() = catalog().getValue("log_follow_latest")
    val log_level: String get() = catalog().getValue("log_level")
    val log_level_desc: String get() = catalog().getValue("log_level_desc")
    val log_mcp_prefix: String get() = catalog().getValue("log_mcp_prefix")
    val log_no_match: String get() = catalog().getValue("log_no_match")
    val log_search_hint: String get() = catalog().getValue("log_search_hint")
    val log_search_results: String get() = catalog().getValue("log_search_results")
    val log_show_last_lines: String get() = catalog().getValue("log_show_last_lines")
    val log_show_lines: String get() = catalog().getValue("log_show_lines")
    val market_empty: String get() = catalog().getValue("market_empty")
    val market_expand_detail: String get() = catalog().getValue("market_expand_detail")
    val market_install: String get() = catalog().getValue("market_install")
    val market_install_failed_download: String get() = catalog().getValue("market_install_failed_download")
    val market_install_failed_install: String get() = catalog().getValue("market_install_failed_install")
    val market_install_failed_no_skill: String get() = catalog().getValue("market_install_failed_no_skill")
    val market_install_no_url: String get() = catalog().getValue("market_install_no_url")
    val market_install_success: String get() = catalog().getValue("market_install_success")
    val market_installed: String get() = catalog().getValue("market_installed")
    val market_installing: String get() = catalog().getValue("market_installing")
    val market_load_more: String get() = catalog().getValue("market_load_more")
    val market_loading_more: String get() = catalog().getValue("market_loading_more")
    val market_matched: String get() = catalog().getValue("market_matched")
    val market_refresh: String get() = catalog().getValue("market_refresh")
    val market_search_hint: String get() = catalog().getValue("market_search_hint")
    val market_sort_by_name: String get() = catalog().getValue("market_sort_by_name")
    val market_sort_by_stars: String get() = catalog().getValue("market_sort_by_stars")
    val market_total_skills: String get() = catalog().getValue("market_total_skills")
    val mcp_add: String get() = catalog().getValue("mcp_add")
    val mcp_add_arg: String get() = catalog().getValue("mcp_add_arg")
    val mcp_add_env_var: String get() = catalog().getValue("mcp_add_env_var")
    val mcp_add_from_json: String get() = catalog().getValue("mcp_add_from_json")
    val mcp_add_from_json_desc: String get() = catalog().getValue("mcp_add_from_json_desc")
    val mcp_add_header: String get() = catalog().getValue("mcp_add_header")
    val mcp_add_manual: String get() = catalog().getValue("mcp_add_manual")
    val mcp_add_manual_desc: String get() = catalog().getValue("mcp_add_manual_desc")
    val mcp_add_sheet_title: String get() = catalog().getValue("mcp_add_sheet_title")
    val mcp_arg_hint: String get() = catalog().getValue("mcp_arg_hint")
    val mcp_arg_value: String get() = catalog().getValue("mcp_arg_value")
    val mcp_args: String get() = catalog().getValue("mcp_args")
    val mcp_auth_advanced: String get() = catalog().getValue("mcp_auth_advanced")
    val mcp_auth_cancel: String get() = catalog().getValue("mcp_auth_cancel")
    val mcp_auth_client_id: String get() = catalog().getValue("mcp_auth_client_id")
    val mcp_auth_client_id_hint: String get() = catalog().getValue("mcp_auth_client_id_hint")
    val mcp_auth_desc: String get() = catalog().getValue("mcp_auth_desc")
    val mcp_auth_enable: String get() = catalog().getValue("mcp_auth_enable")
    val mcp_auth_failed: String get() = catalog().getValue("mcp_auth_failed")
    val mcp_auth_in_progress: String get() = catalog().getValue("mcp_auth_in_progress")
    val mcp_auth_in_progress_hint: String get() = catalog().getValue("mcp_auth_in_progress_hint")
    val mcp_auth_login: String get() = catalog().getValue("mcp_auth_login")
    val mcp_auth_logout: String get() = catalog().getValue("mcp_auth_logout")
    val mcp_auth_reauthorize: String get() = catalog().getValue("mcp_auth_reauthorize")
    val mcp_auth_save_first: String get() = catalog().getValue("mcp_auth_save_first")
    val mcp_auth_server: String get() = catalog().getValue("mcp_auth_server")
    val mcp_auth_server_hint: String get() = catalog().getValue("mcp_auth_server_hint")
    val mcp_auth_status_authorized: String get() = catalog().getValue("mcp_auth_status_authorized")
    val mcp_auth_status_authorizing: String get() = catalog().getValue("mcp_auth_status_authorizing")
    val mcp_auth_status_label: String get() = catalog().getValue("mcp_auth_status_label")
    val mcp_auth_status_unauthorized: String get() = catalog().getValue("mcp_auth_status_unauthorized")
    val mcp_auth_success: String get() = catalog().getValue("mcp_auth_success")
    val mcp_basic_settings: String get() = catalog().getValue("mcp_basic_settings")
    val mcp_batch_delete_confirm: String get() = catalog().getValue("mcp_batch_delete_confirm")
    val mcp_batch_delete_title: String get() = catalog().getValue("mcp_batch_delete_title")
    val mcp_command: String get() = catalog().getValue("mcp_command")
    val mcp_command_hint: String get() = catalog().getValue("mcp_command_hint")
    val mcp_connected: String get() = catalog().getValue("mcp_connected")
    val mcp_connecting: String get() = catalog().getValue("mcp_connecting")
    val mcp_connection_failed: String get() = catalog().getValue("mcp_connection_failed")
    val mcp_custom_headers: String get() = catalog().getValue("mcp_custom_headers")
    val mcp_details: String get() = catalog().getValue("mcp_details")
    val mcp_disabled: String get() = catalog().getValue("mcp_disabled")
    val mcp_edit: String get() = catalog().getValue("mcp_edit")
    val mcp_empty: String get() = catalog().getValue("mcp_empty")
    val mcp_empty_hint: String get() = catalog().getValue("mcp_empty_hint")
    val mcp_enabled: String get() = catalog().getValue("mcp_enabled")
    val mcp_env_name: String get() = catalog().getValue("mcp_env_name")
    val mcp_env_name_hint: String get() = catalog().getValue("mcp_env_name_hint")
    val mcp_env_value: String get() = catalog().getValue("mcp_env_value")
    val mcp_env_value_hint: String get() = catalog().getValue("mcp_env_value_hint")
    val mcp_env_vars: String get() = catalog().getValue("mcp_env_vars")
    val mcp_header_name: String get() = catalog().getValue("mcp_header_name")
    val mcp_header_name_hint: String get() = catalog().getValue("mcp_header_name_hint")
    val mcp_header_value: String get() = catalog().getValue("mcp_header_value")
    val mcp_header_value_hint: String get() = catalog().getValue("mcp_header_value_hint")
    val mcp_json_import_action: String get() = catalog().getValue("mcp_json_import_action")
    val mcp_json_import_empty: String get() = catalog().getValue("mcp_json_import_empty")
    val mcp_json_import_hint: String get() = catalog().getValue("mcp_json_import_hint")
    val mcp_json_import_title: String get() = catalog().getValue("mcp_json_import_title")
    val mcp_local_stdio: String get() = catalog().getValue("mcp_local_stdio")
    val mcp_name_hint: String get() = catalog().getValue("mcp_name_hint")
    val mcp_name_invalid: String get() = catalog().getValue("mcp_name_invalid")
    val mcp_needs_auth: String get() = catalog().getValue("mcp_needs_auth")
    val mcp_no_args: String get() = catalog().getValue("mcp_no_args")
    val mcp_no_custom_headers: String get() = catalog().getValue("mcp_no_custom_headers")
    val mcp_no_description: String get() = catalog().getValue("mcp_no_description")
    val mcp_no_env_vars: String get() = catalog().getValue("mcp_no_env_vars")
    val mcp_no_tools_hint: String get() = catalog().getValue("mcp_no_tools_hint")
    val mcp_not_connected: String get() = catalog().getValue("mcp_not_connected")
    val mcp_refresh_tools: String get() = catalog().getValue("mcp_refresh_tools")
    val mcp_remote_http: String get() = catalog().getValue("mcp_remote_http")
    val mcp_save_first_hint: String get() = catalog().getValue("mcp_save_first_hint")
    val mcp_scope_global: String get() = catalog().getValue("mcp_scope_global")
    val mcp_scope_label: String get() = catalog().getValue("mcp_scope_label")
    val mcp_scope_project: String get() = catalog().getValue("mcp_scope_project")
    val mcp_search_empty: String get() = catalog().getValue("mcp_search_empty")
    val mcp_search_placeholder: String get() = catalog().getValue("mcp_search_placeholder")
    val mcp_server_url: String get() = catalog().getValue("mcp_server_url")
    val mcp_server_url_hint: String get() = catalog().getValue("mcp_server_url_hint")
    val mcp_tools_count: String get() = catalog().getValue("mcp_tools_count")
    val mcp_tools_disable_all: String get() = catalog().getValue("mcp_tools_disable_all")
    val mcp_tools_enable_all: String get() = catalog().getValue("mcp_tools_enable_all")
    val mcp_tools_search: String get() = catalog().getValue("mcp_tools_search")
    val mcp_tools_search_empty: String get() = catalog().getValue("mcp_tools_search_empty")
    val mcp_tools_summary: String get() = catalog().getValue("mcp_tools_summary")
    val mcp_transport_type: String get() = catalog().getValue("mcp_transport_type")
    val mcp_type_stdio: String get() = catalog().getValue("mcp_type_stdio")
    val mcp_view_logs: String get() = catalog().getValue("mcp_view_logs")
    val network_error_connect: String get() = catalog().getValue("network_error_connect")
    val network_error_dns: String get() = catalog().getValue("network_error_dns")
    val network_error_ssl: String get() = catalog().getValue("network_error_ssl")
    val network_error_timeout: String get() = catalog().getValue("network_error_timeout")
    val notification_agent_live_title_multi: String get() = catalog().getValue("notification_agent_live_title_multi")
    val notification_approval_action_allow: String get() = catalog().getValue("notification_approval_action_allow")
    val notification_approval_action_approve_plan: String get() = catalog().getValue("notification_approval_action_approve_plan")
    val notification_approval_action_reject: String get() = catalog().getValue("notification_approval_action_reject")
    val notification_approval_label_permission: String get() = catalog().getValue("notification_approval_label_permission")
    val notification_approval_label_plan: String get() = catalog().getValue("notification_approval_label_plan")
    val notification_approval_label_question: String get() = catalog().getValue("notification_approval_label_question")
    val notification_approval_multi_title: String get() = catalog().getValue("notification_approval_multi_title")
    val notification_approval_needed: String get() = catalog().getValue("notification_approval_needed")
    val notification_approval_title: String get() = catalog().getValue("notification_approval_title")
    val notification_channel_agent_approval: String get() = catalog().getValue("notification_channel_agent_approval")
    val notification_channel_agent_approval_desc: String get() = catalog().getValue("notification_channel_agent_approval_desc")
    val notification_channel_agent_complete: String get() = catalog().getValue("notification_channel_agent_complete")
    val notification_channel_agent_complete_desc: String get() = catalog().getValue("notification_channel_agent_complete_desc")
    val notification_channel_agent_live: String get() = catalog().getValue("notification_channel_agent_live")
    val notification_channel_agent_live_desc: String get() = catalog().getValue("notification_channel_agent_live_desc")
    val notification_channel_terminal_service: String get() = catalog().getValue("notification_channel_terminal_service")
    val notification_channel_terminal_service_desc: String get() = catalog().getValue("notification_channel_terminal_service_desc")
    val notification_keepalive_enabled: String get() = catalog().getValue("notification_keepalive_enabled")
    val notification_keepalive_persistent_sessions: String get() = catalog().getValue("notification_keepalive_persistent_sessions")
    val notification_keepalive_running_sessions: String get() = catalog().getValue("notification_keepalive_running_sessions")
    val onboarding_choose_model_in_sheet_desc: String get() = catalog().getValue("onboarding_choose_model_in_sheet_desc")
    val onboarding_choose_model_in_sheet_title: String get() = catalog().getValue("onboarding_choose_model_in_sheet_title")
    val onboarding_config_provider_desc: String get() = catalog().getValue("onboarding_config_provider_desc")
    val onboarding_config_provider_title: String get() = catalog().getValue("onboarding_config_provider_title")
    val onboarding_done: String get() = catalog().getValue("onboarding_done")
    val onboarding_enter_settings_desc: String get() = catalog().getValue("onboarding_enter_settings_desc")
    val onboarding_enter_settings_title: String get() = catalog().getValue("onboarding_enter_settings_title")
    val onboarding_next: String get() = catalog().getValue("onboarding_next")
    val onboarding_open_model_picker_desc: String get() = catalog().getValue("onboarding_open_model_picker_desc")
    val onboarding_open_model_picker_title: String get() = catalog().getValue("onboarding_open_model_picker_title")
    val onboarding_open_sidebar_desc: String get() = catalog().getValue("onboarding_open_sidebar_desc")
    val onboarding_open_sidebar_title: String get() = catalog().getValue("onboarding_open_sidebar_title")
    val onboarding_provider_add_desc: String get() = catalog().getValue("onboarding_provider_add_desc")
    val onboarding_provider_add_title: String get() = catalog().getValue("onboarding_provider_add_title")
    val onboarding_provider_config_info_desc: String get() = catalog().getValue("onboarding_provider_config_info_desc")
    val onboarding_provider_config_info_title: String get() = catalog().getValue("onboarding_provider_config_info_title")
    val onboarding_provider_fetch_models_desc: String get() = catalog().getValue("onboarding_provider_fetch_models_desc")
    val onboarding_provider_fetch_models_title: String get() = catalog().getValue("onboarding_provider_fetch_models_title")
    val onboarding_provider_select_fetched_model_desc: String get() = catalog().getValue("onboarding_provider_select_fetched_model_desc")
    val onboarding_provider_select_fetched_model_title: String get() = catalog().getValue("onboarding_provider_select_fetched_model_title")
    val onboarding_send_message_desc: String get() = catalog().getValue("onboarding_send_message_desc")
    val onboarding_send_message_title: String get() = catalog().getValue("onboarding_send_message_title")
    val onboarding_skip: String get() = catalog().getValue("onboarding_skip")
    val onboarding_step_badge: String get() = catalog().getValue("onboarding_step_badge")
    val perm_current_project: String get() = catalog().getValue("perm_current_project")
    val perm_current_project_none: String get() = catalog().getValue("perm_current_project_none")
    val perm_deny: String get() = catalog().getValue("perm_deny")
    val perm_disable_safety_interception: String get() = catalog().getValue("perm_disable_safety_interception")
    val perm_disable_safety_interception_desc: String get() = catalog().getValue("perm_disable_safety_interception_desc")
    val perm_entire_tool: String get() = catalog().getValue("perm_entire_tool")
    val perm_global: String get() = catalog().getValue("perm_global")
    val perm_no_global_rules: String get() = catalog().getValue("perm_no_global_rules")
    val perm_no_project_rules: String get() = catalog().getValue("perm_no_project_rules")
    val perm_promote_to_global: String get() = catalog().getValue("perm_promote_to_global")
    val perm_rules_short: String get() = catalog().getValue("perm_rules_short")
    val perm_safety_confirm_action: String get() = catalog().getValue("perm_safety_confirm_action")
    val perm_safety_confirm_action_ready: String get() = catalog().getValue("perm_safety_confirm_action_ready")
    val perm_safety_confirm_message: String get() = catalog().getValue("perm_safety_confirm_message")
    val perm_safety_confirm_title: String get() = catalog().getValue("perm_safety_confirm_title")
    val perm_safety_group: String get() = catalog().getValue("perm_safety_group")
    val plan_mode_already_active: String get() = catalog().getValue("plan_mode_already_active")
    val plan_mode_approved_auto: String get() = catalog().getValue("plan_mode_approved_auto")
    val plan_mode_approved_build: String get() = catalog().getValue("plan_mode_approved_build")
    val plan_mode_enter_action: String get() = catalog().getValue("plan_mode_enter_action")
    val plan_mode_entered: String get() = catalog().getValue("plan_mode_entered")
    val plan_mode_exit_action: String get() = catalog().getValue("plan_mode_exit_action")
    val plan_mode_exited: String get() = catalog().getValue("plan_mode_exited")
    val plan_mode_external_auto: String get() = catalog().getValue("plan_mode_external_auto")
    val plan_mode_external_build: String get() = catalog().getValue("plan_mode_external_build")
    val plan_mode_goal_active: String get() = catalog().getValue("plan_mode_goal_active")
    val plan_mode_invalid_action: String get() = catalog().getValue("plan_mode_invalid_action")
    val plan_mode_not_active: String get() = catalog().getValue("plan_mode_not_active")
    val plan_mode_reason_unspecified: String get() = catalog().getValue("plan_mode_reason_unspecified")
    val plan_mode_refinement_requested: String get() = catalog().getValue("plan_mode_refinement_requested")
    val plan_mode_request_details: String get() = catalog().getValue("plan_mode_request_details")
    val plan_mode_request_summary: String get() = catalog().getValue("plan_mode_request_summary")
    val plan_mode_request_title: String get() = catalog().getValue("plan_mode_request_title")
    val plan_mode_session_missing: String get() = catalog().getValue("plan_mode_session_missing")
    val plan_mode_session_required: String get() = catalog().getValue("plan_mode_session_required")
    val prompts_add_prompt: String get() = catalog().getValue("prompts_add_prompt")
    val prompts_batch_delete_confirm: String get() = catalog().getValue("prompts_batch_delete_confirm")
    val prompts_batch_delete_title: String get() = catalog().getValue("prompts_batch_delete_title")
    val prompts_disable_builtin: String get() = catalog().getValue("prompts_disable_builtin")
    val prompts_disable_builtin_hint: String get() = catalog().getValue("prompts_disable_builtin_hint")
    val prompts_editor_edit: String get() = catalog().getValue("prompts_editor_edit")
    val prompts_editor_new: String get() = catalog().getValue("prompts_editor_new")
    val prompts_empty: String get() = catalog().getValue("prompts_empty")
    val prompts_field_basic: String get() = catalog().getValue("prompts_field_basic")
    val prompts_field_content: String get() = catalog().getValue("prompts_field_content")
    val prompts_field_content_placeholder: String get() = catalog().getValue("prompts_field_content_placeholder")
    val prompts_field_number: String get() = catalog().getValue("prompts_field_number")
    val prompts_field_number_placeholder: String get() = catalog().getValue("prompts_field_number_placeholder")
    val prompts_field_summary: String get() = catalog().getValue("prompts_field_summary")
    val prompts_field_summary_placeholder: String get() = catalog().getValue("prompts_field_summary_placeholder")
    val prompts_field_title: String get() = catalog().getValue("prompts_field_title")
    val prompts_field_title_placeholder: String get() = catalog().getValue("prompts_field_title_placeholder")
    val prompts_help: String get() = catalog().getValue("prompts_help")
    val prompts_reset_confirm: String get() = catalog().getValue("prompts_reset_confirm")
    val prompts_reset_custom: String get() = catalog().getValue("prompts_reset_custom")
    val prompts_sort_long_press: String get() = catalog().getValue("prompts_sort_long_press")
    val prompts_source_builtin: String get() = catalog().getValue("prompts_source_builtin")
    val prompts_source_global: String get() = catalog().getValue("prompts_source_global")
    val prompts_source_local: String get() = catalog().getValue("prompts_source_local")
    val prompts_source_project: String get() = catalog().getValue("prompts_source_project")
    val prompts_title: String get() = catalog().getValue("prompts_title")
    val provider_add: String get() = catalog().getValue("provider_add")
    val provider_add_all_models: String get() = catalog().getValue("provider_add_all_models")
    val provider_add_all_models_confirm: String get() = catalog().getValue("provider_add_all_models_confirm")
    val provider_add_all_models_title: String get() = catalog().getValue("provider_add_all_models_title")
    val provider_add_model: String get() = catalog().getValue("provider_add_model")
    val provider_add_sheet_title: String get() = catalog().getValue("provider_add_sheet_title")
    val provider_api_key: String get() = catalog().getValue("provider_api_key")
    val provider_api_key_required: String get() = catalog().getValue("provider_api_key_required")
    val provider_base_url: String get() = catalog().getValue("provider_base_url")
    val provider_brand_doubao: String get() = catalog().getValue("provider_brand_doubao")
    val provider_brand_hunyuan: String get() = catalog().getValue("provider_brand_hunyuan")
    val provider_brand_siliconflow: String get() = catalog().getValue("provider_brand_siliconflow")
    val provider_brand_tongyi_qianwen: String get() = catalog().getValue("provider_brand_tongyi_qianwen")
    val provider_brand_zhipu: String get() = catalog().getValue("provider_brand_zhipu")
    val provider_cache_anthropic_subtitle: String get() = catalog().getValue("provider_cache_anthropic_subtitle")
    val provider_cache_anthropic_title: String get() = catalog().getValue("provider_cache_anthropic_title")
    val provider_cache_openai_chat_subtitle: String get() = catalog().getValue("provider_cache_openai_chat_subtitle")
    val provider_cache_openai_chat_title: String get() = catalog().getValue("provider_cache_openai_chat_title")
    val provider_config: String get() = catalog().getValue("provider_config")
    val provider_custom: String get() = catalog().getValue("provider_custom")
    val provider_dashboard_clear_script: String get() = catalog().getValue("provider_dashboard_clear_script")
    val provider_dashboard_loading: String get() = catalog().getValue("provider_dashboard_loading")
    val provider_dashboard_no_scripts: String get() = catalog().getValue("provider_dashboard_no_scripts")
    val provider_dashboard_preview_title: String get() = catalog().getValue("provider_dashboard_preview_title")
    val provider_dashboard_raw_output: String get() = catalog().getValue("provider_dashboard_raw_output")
    val provider_dashboard_run_test: String get() = catalog().getValue("provider_dashboard_run_test")
    val provider_dashboard_script: String get() = catalog().getValue("provider_dashboard_script")
    val provider_dashboard_script_desc: String get() = catalog().getValue("provider_dashboard_script_desc")
    val provider_dashboard_script_placeholder: String get() = catalog().getValue("provider_dashboard_script_placeholder")
    val provider_dashboard_select_script: String get() = catalog().getValue("provider_dashboard_select_script")
    val provider_dashboard_select_title: String get() = catalog().getValue("provider_dashboard_select_title")
    val provider_dashboard_test_btn: String get() = catalog().getValue("provider_dashboard_test_btn")
    val provider_dashboard_test_failed: String get() = catalog().getValue("provider_dashboard_test_failed")
    val provider_edit: String get() = catalog().getValue("provider_edit")
    val provider_edit_model: String get() = catalog().getValue("provider_edit_model")
    val provider_fetch_failed: String get() = catalog().getValue("provider_fetch_failed")
    val provider_fetch_http_error: String get() = catalog().getValue("provider_fetch_http_error")
    val provider_fetch_missing_fields: String get() = catalog().getValue("provider_fetch_missing_fields")
    val provider_fetch_missing_fields_detail: String get() = catalog().getValue("provider_fetch_missing_fields_detail")
    val provider_fetch_models: String get() = catalog().getValue("provider_fetch_models")
    val provider_fetch_success: String get() = catalog().getValue("provider_fetch_success")
    val provider_filter_models_hint: String get() = catalog().getValue("provider_filter_models_hint")
    val provider_full_url: String get() = catalog().getValue("provider_full_url")
    val provider_full_url_desc: String get() = catalog().getValue("provider_full_url_desc")
    val provider_headers_add: String get() = catalog().getValue("provider_headers_add")
    val provider_headers_count: String get() = catalog().getValue("provider_headers_count")
    val provider_headers_desc: String get() = catalog().getValue("provider_headers_desc")
    val provider_headers_empty: String get() = catalog().getValue("provider_headers_empty")
    val provider_headers_manage: String get() = catalog().getValue("provider_headers_manage")
    val provider_headers_name: String get() = catalog().getValue("provider_headers_name")
    val provider_headers_name_hint: String get() = catalog().getValue("provider_headers_name_hint")
    val provider_headers_remove: String get() = catalog().getValue("provider_headers_remove")
    val provider_headers_title: String get() = catalog().getValue("provider_headers_title")
    val provider_headers_value: String get() = catalog().getValue("provider_headers_value")
    val provider_headers_value_hint: String get() = catalog().getValue("provider_headers_value_hint")
    val provider_hide_api_key: String get() = catalog().getValue("provider_hide_api_key")
    val provider_interactions_api: String get() = catalog().getValue("provider_interactions_api")
    val provider_model_already_added: String get() = catalog().getValue("provider_model_already_added")
    val provider_model_cap_image_output: String get() = catalog().getValue("provider_model_cap_image_output")
    val provider_model_cap_vision: String get() = catalog().getValue("provider_model_cap_vision")
    val provider_model_capabilities: String get() = catalog().getValue("provider_model_capabilities")
    val provider_model_capability_reasoning: String get() = catalog().getValue("provider_model_capability_reasoning")
    val provider_model_capability_tools: String get() = catalog().getValue("provider_model_capability_tools")
    val provider_model_context_input: String get() = catalog().getValue("provider_model_context_input")
    val provider_model_context_output: String get() = catalog().getValue("provider_model_context_output")
    val provider_model_context_total: String get() = catalog().getValue("provider_model_context_total")
    val provider_model_icon: String get() = catalog().getValue("provider_model_icon")
    val provider_model_name: String get() = catalog().getValue("provider_model_name")
    val provider_model_price_cache_read: String get() = catalog().getValue("provider_model_price_cache_read")
    val provider_model_price_cache_write: String get() = catalog().getValue("provider_model_price_cache_write")
    val provider_model_price_input: String get() = catalog().getValue("provider_model_price_input")
    val provider_model_price_output: String get() = catalog().getValue("provider_model_price_output")
    val provider_model_reasoning_can_disable: String get() = catalog().getValue("provider_model_reasoning_can_disable")
    val provider_model_reasoning_hint: String get() = catalog().getValue("provider_model_reasoning_hint")
    val provider_model_section_price: String get() = catalog().getValue("provider_model_section_price")
    val provider_model_section_reasoning: String get() = catalog().getValue("provider_model_section_reasoning")
    val provider_model_window_invalid: String get() = catalog().getValue("provider_model_window_invalid")
    val provider_models_aligned_removed: String get() = catalog().getValue("provider_models_aligned_removed")
    val provider_models_batch_delete: String get() = catalog().getValue("provider_models_batch_delete")
    val provider_models_batch_delete_confirm: String get() = catalog().getValue("provider_models_batch_delete_confirm")
    val provider_models_batch_delete_title: String get() = catalog().getValue("provider_models_batch_delete_title")
    val provider_models_batch_test: String get() = catalog().getValue("provider_models_batch_test")
    val provider_models_count: String get() = catalog().getValue("provider_models_count")
    val provider_models_count_tag: String get() = catalog().getValue("provider_models_count_tag")
    val provider_models_deselect_all: String get() = catalog().getValue("provider_models_deselect_all")
    val provider_models_select: String get() = catalog().getValue("provider_models_select")
    val provider_models_select_all: String get() = catalog().getValue("provider_models_select_all")
    val provider_models_selected_count: String get() = catalog().getValue("provider_models_selected_count")
    val provider_multi_key_add: String get() = catalog().getValue("provider_multi_key_add")
    val provider_multi_key_cooldown: String get() = catalog().getValue("provider_multi_key_cooldown")
    val provider_multi_key_cooldown_desc: String get() = catalog().getValue("provider_multi_key_cooldown_desc")
    val provider_multi_key_cooldown_off: String get() = catalog().getValue("provider_multi_key_cooldown_off")
    val provider_multi_key_cooldown_value: String get() = catalog().getValue("provider_multi_key_cooldown_value")
    val provider_multi_key_empty: String get() = catalog().getValue("provider_multi_key_empty")
    val provider_multi_key_failover: String get() = catalog().getValue("provider_multi_key_failover")
    val provider_multi_key_item: String get() = catalog().getValue("provider_multi_key_item")
    val provider_multi_key_list: String get() = catalog().getValue("provider_multi_key_list")
    val provider_multi_key_manage: String get() = catalog().getValue("provider_multi_key_manage")
    val provider_multi_key_status_codes: String get() = catalog().getValue("provider_multi_key_status_codes")
    val provider_multi_key_status_codes_hint: String get() = catalog().getValue("provider_multi_key_status_codes_hint")
    val provider_multi_key_strategy: String get() = catalog().getValue("provider_multi_key_strategy")
    val provider_multi_key_strategy_round_robin: String get() = catalog().getValue("provider_multi_key_strategy_round_robin")
    val provider_multi_key_strategy_round_robin_desc: String get() = catalog().getValue("provider_multi_key_strategy_round_robin_desc")
    val provider_multi_key_strategy_sequential: String get() = catalog().getValue("provider_multi_key_strategy_sequential")
    val provider_multi_key_strategy_sequential_desc: String get() = catalog().getValue("provider_multi_key_strategy_sequential_desc")
    val provider_multi_key_subtitle: String get() = catalog().getValue("provider_multi_key_subtitle")
    val provider_multi_key_summary: String get() = catalog().getValue("provider_multi_key_summary")
    val provider_multi_key_title: String get() = catalog().getValue("provider_multi_key_title")
    val provider_multi_key_weight: String get() = catalog().getValue("provider_multi_key_weight")
    val provider_multi_key_weight_hint: String get() = catalog().getValue("provider_multi_key_weight_hint")
    val provider_new: String get() = catalog().getValue("provider_new")
    val provider_no_matching_models: String get() = catalog().getValue("provider_no_matching_models")
    val provider_no_models: String get() = catalog().getValue("provider_no_models")
    val provider_no_models_hint: String get() = catalog().getValue("provider_no_models_hint")
    val provider_please_wait: String get() = catalog().getValue("provider_please_wait")
    val provider_preset_empty: String get() = catalog().getValue("provider_preset_empty")
    val provider_preset_loading: String get() = catalog().getValue("provider_preset_loading")
    val provider_proxy_enable: String get() = catalog().getValue("provider_proxy_enable")
    val provider_proxy_enable_desc: String get() = catalog().getValue("provider_proxy_enable_desc")
    val provider_proxy_off: String get() = catalog().getValue("provider_proxy_off")
    val provider_proxy_section: String get() = catalog().getValue("provider_proxy_section")
    val provider_register_now: String get() = catalog().getValue("provider_register_now")
    val provider_response_api: String get() = catalog().getValue("provider_response_api")
    val provider_script_param_add: String get() = catalog().getValue("provider_script_param_add")
    val provider_script_param_key: String get() = catalog().getValue("provider_script_param_key")
    val provider_script_param_key_hint: String get() = catalog().getValue("provider_script_param_key_hint")
    val provider_script_param_manage: String get() = catalog().getValue("provider_script_param_manage")
    val provider_script_param_remove: String get() = catalog().getValue("provider_script_param_remove")
    val provider_script_param_value: String get() = catalog().getValue("provider_script_param_value")
    val provider_script_param_value_hint: String get() = catalog().getValue("provider_script_param_value_hint")
    val provider_script_params_count: String get() = catalog().getValue("provider_script_params_count")
    val provider_script_params_desc: String get() = catalog().getValue("provider_script_params_desc")
    val provider_script_params_empty: String get() = catalog().getValue("provider_script_params_empty")
    val provider_script_params_title: String get() = catalog().getValue("provider_script_params_title")
    val provider_section_basic: String get() = catalog().getValue("provider_section_basic")
    val provider_section_dashboard: String get() = catalog().getValue("provider_section_dashboard")
    val provider_section_options: String get() = catalog().getValue("provider_section_options")
    val provider_section_type: String get() = catalog().getValue("provider_section_type")
    val provider_show_api_key: String get() = catalog().getValue("provider_show_api_key")
    val provider_sort_long_press: String get() = catalog().getValue("provider_sort_long_press")
    val provider_test: String get() = catalog().getValue("provider_test")
    val provider_test_detail_title: String get() = catalog().getValue("provider_test_detail_title")
    val provider_test_error_detail: String get() = catalog().getValue("provider_test_error_detail")
    val provider_test_req_body: String get() = catalog().getValue("provider_test_req_body")
    val provider_test_req_headers: String get() = catalog().getValue("provider_test_req_headers")
    val provider_test_req_url: String get() = catalog().getValue("provider_test_req_url")
    val provider_test_resp_body: String get() = catalog().getValue("provider_test_resp_body")
    val provider_test_resp_headers: String get() = catalog().getValue("provider_test_resp_headers")
    val provider_test_success: String get() = catalog().getValue("provider_test_success")
    val providers_batch_delete_confirm: String get() = catalog().getValue("providers_batch_delete_confirm")
    val providers_batch_delete_title: String get() = catalog().getValue("providers_batch_delete_title")
    val providers_empty: String get() = catalog().getValue("providers_empty")
    val proxy_auth: String get() = catalog().getValue("proxy_auth")
    val proxy_enable: String get() = catalog().getValue("proxy_enable")
    val proxy_enable_subtitle: String get() = catalog().getValue("proxy_enable_subtitle")
    val proxy_host: String get() = catalog().getValue("proxy_host")
    val proxy_host_placeholder: String get() = catalog().getValue("proxy_host_placeholder")
    val proxy_no_proxy: String get() = catalog().getValue("proxy_no_proxy")
    val proxy_no_proxy_hint: String get() = catalog().getValue("proxy_no_proxy_hint")
    val proxy_password: String get() = catalog().getValue("proxy_password")
    val proxy_port: String get() = catalog().getValue("proxy_port")
    val proxy_port_placeholder: String get() = catalog().getValue("proxy_port_placeholder")
    val proxy_server: String get() = catalog().getValue("proxy_server")
    val proxy_test: String get() = catalog().getValue("proxy_test")
    val proxy_test_connect_failed: String get() = catalog().getValue("proxy_test_connect_failed")
    val proxy_test_http_error: String get() = catalog().getValue("proxy_test_http_error")
    val proxy_test_invalid_config: String get() = catalog().getValue("proxy_test_invalid_config")
    val proxy_test_ok: String get() = catalog().getValue("proxy_test_ok")
    val proxy_test_title: String get() = catalog().getValue("proxy_test_title")
    val proxy_test_url_label: String get() = catalog().getValue("proxy_test_url_label")
    val proxy_test_url_placeholder: String get() = catalog().getValue("proxy_test_url_placeholder")
    val proxy_testing: String get() = catalog().getValue("proxy_testing")
    val proxy_title: String get() = catalog().getValue("proxy_title")
    val proxy_type: String get() = catalog().getValue("proxy_type")
    val proxy_type_http: String get() = catalog().getValue("proxy_type_http")
    val proxy_type_socks5: String get() = catalog().getValue("proxy_type_socks5")
    val proxy_username: String get() = catalog().getValue("proxy_username")
    val remote_add_channel_first: String get() = catalog().getValue("remote_add_channel_first")
    val remote_add_connection: String get() = catalog().getValue("remote_add_connection")
    val remote_add_workspace: String get() = catalog().getValue("remote_add_workspace")
    val remote_auth_key: String get() = catalog().getValue("remote_auth_key")
    val remote_auth_password: String get() = catalog().getValue("remote_auth_password")
    val remote_auto_connect_and_sync: String get() = catalog().getValue("remote_auto_connect_and_sync")
    val remote_auto_connect_on_start: String get() = catalog().getValue("remote_auto_connect_on_start")
    val remote_browse_dir: String get() = catalog().getValue("remote_browse_dir")
    val remote_clear_index_done: String get() = catalog().getValue("remote_clear_index_done")
    val remote_confirm_select: String get() = catalog().getValue("remote_confirm_select")
    val remote_connect_failed: String get() = catalog().getValue("remote_connect_failed")
    val remote_connect_success: String get() = catalog().getValue("remote_connect_success")
    val remote_connection_name_hint: String get() = catalog().getValue("remote_connection_name_hint")
    val remote_connection_sort_long_press: String get() = catalog().getValue("remote_connection_sort_long_press")
    val remote_current_path: String get() = catalog().getValue("remote_current_path")
    val remote_delete_connection_confirm: String get() = catalog().getValue("remote_delete_connection_confirm")
    val remote_delete_mount_confirm: String get() = catalog().getValue("remote_delete_mount_confirm")
    val remote_download_all_failed: String get() = catalog().getValue("remote_download_all_failed")
    val remote_download_all_success: String get() = catalog().getValue("remote_download_all_success")
    val remote_edit_connection: String get() = catalog().getValue("remote_edit_connection")
    val remote_edit_workspace: String get() = catalog().getValue("remote_edit_workspace")
    val remote_go_add: String get() = catalog().getValue("remote_go_add")
    val remote_hint_title: String get() = catalog().getValue("remote_hint_title")
    val remote_host_address: String get() = catalog().getValue("remote_host_address")
    val remote_key_passphrase: String get() = catalog().getValue("remote_key_passphrase")
    val remote_link_channel: String get() = catalog().getValue("remote_link_channel")
    val remote_load_failed: String get() = catalog().getValue("remote_load_failed")
    val remote_login_key: String get() = catalog().getValue("remote_login_key")
    val remote_map_to_local: String get() = catalog().getValue("remote_map_to_local")
    val remote_mount_already_exists: String get() = catalog().getValue("remote_mount_already_exists")
    val remote_no_connections: String get() = catalog().getValue("remote_no_connections")
    val remote_no_connections_desc: String get() = catalog().getValue("remote_no_connections_desc")
    val remote_no_keys_hint: String get() = catalog().getValue("remote_no_keys_hint")
    val remote_no_local_workspace: String get() = catalog().getValue("remote_no_local_workspace")
    val remote_no_workspaces: String get() = catalog().getValue("remote_no_workspaces")
    val remote_no_workspaces_desc: String get() = catalog().getValue("remote_no_workspaces_desc")
    val remote_parent_dir: String get() = catalog().getValue("remote_parent_dir")
    val remote_password: String get() = catalog().getValue("remote_password")
    val remote_port: String get() = catalog().getValue("remote_port")
    val remote_select_channel: String get() = catalog().getValue("remote_select_channel")
    val remote_select_key: String get() = catalog().getValue("remote_select_key")
    val remote_select_local_workspace: String get() = catalog().getValue("remote_select_local_workspace")
    val remote_select_remote_dir: String get() = catalog().getValue("remote_select_remote_dir")
    val remote_tab_connections: String get() = catalog().getValue("remote_tab_connections")
    val remote_tab_ftp: String get() = catalog().getValue("remote_tab_ftp")
    val remote_tab_mounts: String get() = catalog().getValue("remote_tab_mounts")
    val remote_tab_sync: String get() = catalog().getValue("remote_tab_sync")
    val remote_target_dir: String get() = catalog().getValue("remote_target_dir")
    val remote_test_connection: String get() = catalog().getValue("remote_test_connection")
    val remote_toggle_password: String get() = catalog().getValue("remote_toggle_password")
    val remote_unknown_error: String get() = catalog().getValue("remote_unknown_error")
    val remote_upload_failed: String get() = catalog().getValue("remote_upload_failed")
    val remote_upload_full_failed: String get() = catalog().getValue("remote_upload_full_failed")
    val remote_upload_full_success: String get() = catalog().getValue("remote_upload_full_success")
    val remote_upload_success: String get() = catalog().getValue("remote_upload_success")
    val remote_workspace_title: String get() = catalog().getValue("remote_workspace_title")
    val retry_error_connection_refused: String get() = catalog().getValue("retry_error_connection_refused")
    val retry_error_connection_reset: String get() = catalog().getValue("retry_error_connection_reset")
    val retry_error_dns_failed: String get() = catalog().getValue("retry_error_dns_failed")
    val retry_error_network: String get() = catalog().getValue("retry_error_network")
    val retry_error_rate_limit: String get() = catalog().getValue("retry_error_rate_limit")
    val retry_error_server: String get() = catalog().getValue("retry_error_server")
    val retry_error_server_overloaded: String get() = catalog().getValue("retry_error_server_overloaded")
    val retry_error_ssl: String get() = catalog().getValue("retry_error_ssl")
    val retry_error_timeout: String get() = catalog().getValue("retry_error_timeout")
    val retry_error_unknown: String get() = catalog().getValue("retry_error_unknown")
    val retry_error_with_code: String get() = catalog().getValue("retry_error_with_code")
    val session_group_last_30_days: String get() = catalog().getValue("session_group_last_30_days")
    val session_group_last_7_days: String get() = catalog().getValue("session_group_last_7_days")
    val session_group_month_format: String get() = catalog().getValue("session_group_month_format")
    val session_group_pinned: String get() = catalog().getValue("session_group_pinned")
    val session_group_today: String get() = catalog().getValue("session_group_today")
    val session_group_yesterday: String get() = catalog().getValue("session_group_yesterday")
    val settings_about: String get() = catalog().getValue("settings_about")
    val settings_add_mcp_server: String get() = catalog().getValue("settings_add_mcp_server")
    val settings_add_provider: String get() = catalog().getValue("settings_add_provider")
    val settings_agent_sound_subtitle: String get() = catalog().getValue("settings_agent_sound_subtitle")
    val settings_agent_sound_title: String get() = catalog().getValue("settings_agent_sound_title")
    val settings_app_permissions: String get() = catalog().getValue("settings_app_permissions")
    val settings_approval_notification_subtitle: String get() = catalog().getValue("settings_approval_notification_subtitle")
    val settings_approval_notification_title: String get() = catalog().getValue("settings_approval_notification_title")
    val settings_auto_remove_stale_models: String get() = catalog().getValue("settings_auto_remove_stale_models")
    val settings_auto_remove_stale_models_desc: String get() = catalog().getValue("settings_auto_remove_stale_models_desc")
    val settings_autostart_subtitle: String get() = catalog().getValue("settings_autostart_subtitle")
    val settings_autostart_title: String get() = catalog().getValue("settings_autostart_title")
    val settings_background_image: String get() = catalog().getValue("settings_background_image")
    val settings_background_image_alpha: String get() = catalog().getValue("settings_background_image_alpha")
    val settings_background_image_none: String get() = catalog().getValue("settings_background_image_none")
    val settings_background_image_pick: String get() = catalog().getValue("settings_background_image_pick")
    val settings_background_image_remove: String get() = catalog().getValue("settings_background_image_remove")
    val team_member_add: String get() = catalog().getValue("team_member_add")
    val team_editor_add_agent: String get() = catalog().getValue("team_editor_add_agent")
    val team_editor_add_model: String get() = catalog().getValue("team_editor_add_model")
    val settings_background_kind_gif: String get() = catalog().getValue("settings_background_kind_gif")
    val settings_background_kind_video: String get() = catalog().getValue("settings_background_kind_video")
    val settings_teams: String get() = catalog().getValue("settings_teams")
    val chat_open_teams: String get() = catalog().getValue("chat_open_teams")
    val teams_new: String get() = catalog().getValue("teams_new")
    val teams_empty: String get() = catalog().getValue("teams_empty")
    val teams_empty_hint: String get() = catalog().getValue("teams_empty_hint")
    val teams_list_header: String get() = catalog().getValue("teams_list_header")
    val teams_member_count: String get() = catalog().getValue("teams_member_count")
    val team_editor_new: String get() = catalog().getValue("team_editor_new")
    val team_editor_edit: String get() = catalog().getValue("team_editor_edit")
    val team_editor_basic: String get() = catalog().getValue("team_editor_basic")
    val team_editor_owner: String get() = catalog().getValue("team_editor_owner")
    val team_editor_owner_name: String get() = catalog().getValue("team_editor_owner_name")
    val team_editor_pick_avatar: String get() = catalog().getValue("team_editor_pick_avatar")
    val team_editor_members: String get() = catalog().getValue("team_editor_members")
    val team_editor_members_hint: String get() = catalog().getValue("team_editor_members_hint")
    val team_editor_rename_member: String get() = catalog().getValue("team_editor_rename_member")
    val team_editor_remove_member: String get() = catalog().getValue("team_editor_remove_member")
    val team_editor_autocompose: String get() = catalog().getValue("team_editor_autocompose")
    val team_editor_autocompose_action: String get() = catalog().getValue("team_editor_autocompose_action")
    val team_editor_autocompose_placeholder: String get() = catalog().getValue("team_editor_autocompose_placeholder")
    val team_editor_autocomposing: String get() = catalog().getValue("team_editor_autocomposing")
    val team_editor_no_agents: String get() = catalog().getValue("team_editor_no_agents")
    val team_editor_no_models: String get() = catalog().getValue("team_editor_no_models")
    val team_editor_no_members: String get() = catalog().getValue("team_editor_no_members")
    val team_member_lead: String get() = catalog().getValue("team_member_lead")
    val team_member_prompt_hint: String get() = catalog().getValue("team_member_prompt_hint")
    val team_name_hint: String get() = catalog().getValue("team_name_hint")
    val team_description_hint: String get() = catalog().getValue("team_description_hint")
    val team_strategy_hint: String get() = catalog().getValue("team_strategy_hint")
    val team_error_invalid_name: String get() = catalog().getValue("team_error_invalid_name")
    val team_error_name_conflict: String get() = catalog().getValue("team_error_name_conflict")
    val team_error_member_limit: String get() = catalog().getValue("team_error_member_limit")
    val team_error_no_members: String get() = catalog().getValue("team_error_no_members")
    val team_delete: String get() = catalog().getValue("team_delete")
    val team_delete_confirm: String get() = catalog().getValue("team_delete_confirm")
    val team_chat_title: String get() = catalog().getValue("team_chat_title")
    val team_chat_input_hint: String get() = catalog().getValue("team_chat_input_hint")
    val team_chat_send: String get() = catalog().getValue("team_chat_send")
    val team_chat_stop: String get() = catalog().getValue("team_chat_stop")
    val team_chat_thinking: String get() = catalog().getValue("team_chat_thinking")
    val team_chat_empty: String get() = catalog().getValue("team_chat_empty")
    val team_chat_empty_hint: String get() = catalog().getValue("team_chat_empty_hint")
    val team_chat_clear: String get() = catalog().getValue("team_chat_clear")
    val team_chat_clear_confirm: String get() = catalog().getValue("team_chat_clear_confirm")
    val settings_backup: String get() = catalog().getValue("settings_backup")
    val settings_category_ai: String get() = catalog().getValue("settings_category_ai")
    val settings_category_background: String get() = catalog().getValue("settings_category_background")
    val settings_category_data: String get() = catalog().getValue("settings_category_data")
    val settings_category_environment: String get() = catalog().getValue("settings_category_environment")
    val settings_category_general: String get() = catalog().getValue("settings_category_general")
    val settings_category_help: String get() = catalog().getValue("settings_category_help")
    val settings_category_permissions: String get() = catalog().getValue("settings_category_permissions")
    val settings_category_system_permissions: String get() = catalog().getValue("settings_category_system_permissions")
    val settings_compaction_follow_chat: String get() = catalog().getValue("settings_compaction_follow_chat")
    val settings_compaction_model: String get() = catalog().getValue("settings_compaction_model")
    val settings_compaction_threshold: String get() = catalog().getValue("settings_compaction_threshold")
    val settings_compaction_threshold_desc: String get() = catalog().getValue("settings_compaction_threshold_desc")
    val settings_compaction_threshold_input_hint: String get() = catalog().getValue("settings_compaction_threshold_input_hint")
    val settings_container: String get() = catalog().getValue("settings_container")
    val settings_default_models: String get() = catalog().getValue("settings_default_models")
    val settings_delete_external_workspace_sessions: String get() = catalog().getValue("settings_delete_external_workspace_sessions")
    val settings_delete_external_workspace_sessions_desc: String get() = catalog().getValue("settings_delete_external_workspace_sessions_desc")
    val settings_enter_to_send: String get() = catalog().getValue("settings_enter_to_send")
    val settings_enter_to_send_desc: String get() = catalog().getValue("settings_enter_to_send_desc")
    val settings_first_byte_timeout: String get() = catalog().getValue("settings_first_byte_timeout")
    val settings_first_byte_timeout_desc: String get() = catalog().getValue("settings_first_byte_timeout_desc")
    val settings_general: String get() = catalog().getValue("settings_general")
    val settings_general_goal: String get() = catalog().getValue("settings_general_goal")
    val settings_general_input: String get() = catalog().getValue("settings_general_input")
    val settings_general_models: String get() = catalog().getValue("settings_general_models")
    val settings_general_network: String get() = catalog().getValue("settings_general_network")
    val settings_general_session: String get() = catalog().getValue("settings_general_session")
    val settings_general_tools: String get() = catalog().getValue("settings_general_tools")
    val settings_goal_max_tokens: String get() = catalog().getValue("settings_goal_max_tokens")
    val settings_goal_max_tokens_desc: String get() = catalog().getValue("settings_goal_max_tokens_desc")
    val settings_goal_max_tokens_input_hint: String get() = catalog().getValue("settings_goal_max_tokens_input_hint")
    val settings_goal_max_turns: String get() = catalog().getValue("settings_goal_max_turns")
    val settings_goal_max_turns_desc: String get() = catalog().getValue("settings_goal_max_turns_desc")
    val settings_goal_max_turns_input_hint: String get() = catalog().getValue("settings_goal_max_turns_input_hint")
    val settings_image_gen_model: String get() = catalog().getValue("settings_image_gen_model")
    val settings_image_gen_unconfigured: String get() = catalog().getValue("settings_image_gen_unconfigured")
    val settings_keepalive_subtitle: String get() = catalog().getValue("settings_keepalive_subtitle")
    val settings_keepalive_title: String get() = catalog().getValue("settings_keepalive_title")
    val settings_language: String get() = catalog().getValue("settings_language")
    val settings_live_notification_subtitle: String get() = catalog().getValue("settings_live_notification_subtitle")
    val settings_live_notification_title: String get() = catalog().getValue("settings_live_notification_title")
    val settings_log: String get() = catalog().getValue("settings_log")
    val settings_log_no_files: String get() = catalog().getValue("settings_log_no_files")
    val settings_log_read_failed: String get() = catalog().getValue("settings_log_read_failed")
    val settings_max_network_retries: String get() = catalog().getValue("settings_max_network_retries")
    val settings_max_network_retries_desc: String get() = catalog().getValue("settings_max_network_retries_desc")
    val settings_mb_value: String get() = catalog().getValue("settings_mb_value")
    val settings_mcp: String get() = catalog().getValue("settings_mcp")
    val settings_models_fetch_failed: String get() = catalog().getValue("settings_models_fetch_failed")
    val settings_network_guide_brand_huawei: String get() = catalog().getValue("settings_network_guide_brand_huawei")
    val settings_network_guide_brand_oppo: String get() = catalog().getValue("settings_network_guide_brand_oppo")
    val settings_network_guide_brand_samsung: String get() = catalog().getValue("settings_network_guide_brand_samsung")
    val settings_network_guide_brand_vivo: String get() = catalog().getValue("settings_network_guide_brand_vivo")
    val settings_network_guide_brand_xiaomi: String get() = catalog().getValue("settings_network_guide_brand_xiaomi")
    val settings_network_guide_intro: String get() = catalog().getValue("settings_network_guide_intro")
    val settings_network_guide_more: String get() = catalog().getValue("settings_network_guide_more")
    val settings_network_guide_step_battery: String get() = catalog().getValue("settings_network_guide_step_battery")
    val settings_network_guide_step_keepalive: String get() = catalog().getValue("settings_network_guide_step_keepalive")
    val settings_network_guide_step_lock: String get() = catalog().getValue("settings_network_guide_step_lock")
    val settings_network_guide_subtitle: String get() = catalog().getValue("settings_network_guide_subtitle")
    val settings_network_guide_title: String get() = catalog().getValue("settings_network_guide_title")
    val settings_percent_value: String get() = catalog().getValue("settings_percent_value")
    val settings_permission_apk_install: String get() = catalog().getValue("settings_permission_apk_install")
    val settings_permission_apk_install_desc: String get() = catalog().getValue("settings_permission_apk_install_desc")
    val settings_permission_battery: String get() = catalog().getValue("settings_permission_battery")
    val settings_permission_battery_desc: String get() = catalog().getValue("settings_permission_battery_desc")
    val settings_permission_denied: String get() = catalog().getValue("settings_permission_denied")
    val settings_permission_granted: String get() = catalog().getValue("settings_permission_granted")
    val settings_permission_notification_denied_desc: String get() = catalog().getValue("settings_permission_notification_denied_desc")
    val settings_permission_notification_title: String get() = catalog().getValue("settings_permission_notification_title")
    val settings_permission_storage: String get() = catalog().getValue("settings_permission_storage")
    val settings_permission_storage_denied_desc: String get() = catalog().getValue("settings_permission_storage_denied_desc")
    val settings_permission_storage_desc: String get() = catalog().getValue("settings_permission_storage_desc")
    val settings_permissions: String get() = catalog().getValue("settings_permissions")
    val settings_provider_editor: String get() = catalog().getValue("settings_provider_editor")
    val settings_providers: String get() = catalog().getValue("settings_providers")
    val settings_reconnect: String get() = catalog().getValue("settings_reconnect")
    val settings_refresh_logs: String get() = catalog().getValue("settings_refresh_logs")
    val settings_remote_servers: String get() = catalog().getValue("settings_remote_servers")
    val settings_rerun_onboarding: String get() = catalog().getValue("settings_rerun_onboarding")
    val settings_retries_count: String get() = catalog().getValue("settings_retries_count")
    val settings_retries_input_hint: String get() = catalog().getValue("settings_retries_input_hint")
    val settings_screen_on_subtitle: String get() = catalog().getValue("settings_screen_on_subtitle")
    val settings_screen_on_title: String get() = catalog().getValue("settings_screen_on_title")
    val settings_select_section_hint: String get() = catalog().getValue("settings_select_section_hint")
    val settings_sendfile_max_size: String get() = catalog().getValue("settings_sendfile_max_size")
    val settings_sendfile_max_size_desc: String get() = catalog().getValue("settings_sendfile_max_size_desc")
    val settings_sendfile_max_size_input_hint: String get() = catalog().getValue("settings_sendfile_max_size_input_hint")
    val settings_shizuku: String get() = catalog().getValue("settings_shizuku")
    val settings_shizuku_hint_denied: String get() = catalog().getValue("settings_shizuku_hint_denied")
    val settings_shizuku_hint_not_installed: String get() = catalog().getValue("settings_shizuku_hint_not_installed")
    val settings_shizuku_hint_not_running: String get() = catalog().getValue("settings_shizuku_hint_not_running")
    val settings_shizuku_hint_ready: String get() = catalog().getValue("settings_shizuku_hint_ready")
    val settings_shizuku_status_denied: String get() = catalog().getValue("settings_shizuku_status_denied")
    val settings_shizuku_status_not_installed: String get() = catalog().getValue("settings_shizuku_status_not_installed")
    val settings_shizuku_status_not_running: String get() = catalog().getValue("settings_shizuku_status_not_running")
    val settings_shizuku_status_ready: String get() = catalog().getValue("settings_shizuku_status_ready")
    val settings_skills: String get() = catalog().getValue("settings_skills")
    val settings_startup_session: String get() = catalog().getValue("settings_startup_session")
    val settings_startup_session_new: String get() = catalog().getValue("settings_startup_session_new")
    val settings_startup_session_new_desc: String get() = catalog().getValue("settings_startup_session_new_desc")
    val settings_startup_session_recent: String get() = catalog().getValue("settings_startup_session_recent")
    val settings_startup_session_recent_desc: String get() = catalog().getValue("settings_startup_session_recent_desc")
    val settings_storage: String get() = catalog().getValue("settings_storage")
    val settings_stream_idle_timeout: String get() = catalog().getValue("settings_stream_idle_timeout")
    val settings_stream_idle_timeout_desc: String get() = catalog().getValue("settings_stream_idle_timeout_desc")
    val settings_subagent_tier_light: String get() = catalog().getValue("settings_subagent_tier_light")
    val settings_subagent_tier_standard: String get() = catalog().getValue("settings_subagent_tier_standard")
    val settings_subagent_tier_strong: String get() = catalog().getValue("settings_subagent_tier_strong")
    val settings_subagent_tier_unconfigured: String get() = catalog().getValue("settings_subagent_tier_unconfigured")
    val settings_subagent_tiers_header: String get() = catalog().getValue("settings_subagent_tiers_header")
    val settings_subagents: String get() = catalog().getValue("settings_subagents")
    val settings_theme_title: String get() = catalog().getValue("settings_theme_title")
    val settings_timeout_input_hint: String get() = catalog().getValue("settings_timeout_input_hint")
    val settings_timeout_seconds: String get() = catalog().getValue("settings_timeout_seconds")
    val settings_timeout_unlimited: String get() = catalog().getValue("settings_timeout_unlimited")
    val settings_title: String get() = catalog().getValue("settings_title")
    val settings_title_follow_chat: String get() = catalog().getValue("settings_title_follow_chat")
    val settings_title_model: String get() = catalog().getValue("settings_title_model")
    val settings_token_stats_cache_hit_rate: String get() = catalog().getValue("settings_token_stats_cache_hit_rate")
    val settings_token_stats_cache_read_write: String get() = catalog().getValue("settings_token_stats_cache_read_write")
    val settings_token_stats_calls_suffix: String get() = catalog().getValue("settings_token_stats_calls_suffix")
    val settings_token_stats_calls_suffix2: String get() = catalog().getValue("settings_token_stats_calls_suffix2")
    val settings_token_stats_col_cache_hit_rate: String get() = catalog().getValue("settings_token_stats_col_cache_hit_rate")
    val settings_token_stats_col_cached_read: String get() = catalog().getValue("settings_token_stats_col_cached_read")
    val settings_token_stats_col_cached_write: String get() = catalog().getValue("settings_token_stats_col_cached_write")
    val settings_token_stats_col_cost: String get() = catalog().getValue("settings_token_stats_col_cost")
    val settings_token_stats_col_duration: String get() = catalog().getValue("settings_token_stats_col_duration")
    val settings_token_stats_col_input: String get() = catalog().getValue("settings_token_stats_col_input")
    val settings_token_stats_col_model: String get() = catalog().getValue("settings_token_stats_col_model")
    val settings_token_stats_col_output: String get() = catalog().getValue("settings_token_stats_col_output")
    val settings_token_stats_col_provider: String get() = catalog().getValue("settings_token_stats_col_provider")
    val settings_token_stats_col_reasoning_effort: String get() = catalog().getValue("settings_token_stats_col_reasoning_effort")
    val settings_token_stats_col_reasoning_wire: String get() = catalog().getValue("settings_token_stats_col_reasoning_wire")
    val settings_token_stats_col_status: String get() = catalog().getValue("settings_token_stats_col_status")
    val settings_token_stats_col_time: String get() = catalog().getValue("settings_token_stats_col_time")
    val settings_token_stats_col_ttfb: String get() = catalog().getValue("settings_token_stats_col_ttfb")
    val settings_token_stats_cost: String get() = catalog().getValue("settings_token_stats_cost")
    val settings_token_stats_filter_all_models: String get() = catalog().getValue("settings_token_stats_filter_all_models")
    val settings_token_stats_filter_all_providers: String get() = catalog().getValue("settings_token_stats_filter_all_providers")
    val settings_token_stats_filter_clear: String get() = catalog().getValue("settings_token_stats_filter_clear")
    val settings_token_stats_filter_model: String get() = catalog().getValue("settings_token_stats_filter_model")
    val settings_token_stats_filter_provider: String get() = catalog().getValue("settings_token_stats_filter_provider")
    val settings_token_stats_legend_cached: String get() = catalog().getValue("settings_token_stats_legend_cached")
    val settings_token_stats_legend_input: String get() = catalog().getValue("settings_token_stats_legend_input")
    val settings_token_stats_legend_output: String get() = catalog().getValue("settings_token_stats_legend_output")
    val settings_token_stats_model: String get() = catalog().getValue("settings_token_stats_model")
    val settings_token_stats_no_data: String get() = catalog().getValue("settings_token_stats_no_data")
    val settings_token_stats_page: String get() = catalog().getValue("settings_token_stats_page")
    val settings_token_stats_period_30d: String get() = catalog().getValue("settings_token_stats_period_30d")
    val settings_token_stats_period_7d: String get() = catalog().getValue("settings_token_stats_period_7d")
    val settings_token_stats_period_all: String get() = catalog().getValue("settings_token_stats_period_all")
    val settings_token_stats_period_today: String get() = catalog().getValue("settings_token_stats_period_today")
    val settings_token_stats_provider: String get() = catalog().getValue("settings_token_stats_provider")
    val settings_token_stats_provider_keys: String get() = catalog().getValue("settings_token_stats_provider_keys")
    val settings_token_stats_recent_calls: String get() = catalog().getValue("settings_token_stats_recent_calls")
    val settings_token_stats_requests: String get() = catalog().getValue("settings_token_stats_requests")
    val settings_token_stats_requests_unit: String get() = catalog().getValue("settings_token_stats_requests_unit")
    val settings_token_stats_reset: String get() = catalog().getValue("settings_token_stats_reset")
    val settings_token_stats_reset_confirm: String get() = catalog().getValue("settings_token_stats_reset_confirm")
    val settings_token_stats_reset_title: String get() = catalog().getValue("settings_token_stats_reset_title")
    val settings_token_stats_retries: String get() = catalog().getValue("settings_token_stats_retries")
    val settings_token_stats_success_rate: String get() = catalog().getValue("settings_token_stats_success_rate")
    val settings_token_stats_title: String get() = catalog().getValue("settings_token_stats_title")
    val settings_token_stats_total: String get() = catalog().getValue("settings_token_stats_total")
    val settings_token_stats_trend: String get() = catalog().getValue("settings_token_stats_trend")
    val settings_token_stats_unknown: String get() = catalog().getValue("settings_token_stats_unknown")
    val settings_tokens_value: String get() = catalog().getValue("settings_tokens_value")
    val settings_turns_value: String get() = catalog().getValue("settings_turns_value")
    val settings_user_guide: String get() = catalog().getValue("settings_user_guide")
    val settings_vision_follow_chat: String get() = catalog().getValue("settings_vision_follow_chat")
    val settings_vision_model: String get() = catalog().getValue("settings_vision_model")
    val plugin_market_title: String get() = catalog().getValue("plugin_market_title")
    val plugin_market_section: String get() = catalog().getValue("plugin_market_section")
    val plugin_installed_section: String get() = catalog().getValue("plugin_installed_section")
    val plugin_install: String get() = catalog().getValue("plugin_install")
    val plugin_install_url: String get() = catalog().getValue("plugin_install_url")
    val plugin_installed: String get() = catalog().getValue("plugin_installed")
    val plugin_search_hint: String get() = catalog().getValue("plugin_search_hint")
    val plugin_empty: String get() = catalog().getValue("plugin_empty")
    val plugin_by: String get() = catalog().getValue("plugin_by")
    val plugin_enable: String get() = catalog().getValue("plugin_enable")
    val plugin_disable: String get() = catalog().getValue("plugin_disable")
    val plugin_delete_title: String get() = catalog().getValue("plugin_delete_title")
    val plugin_delete_message: String get() = catalog().getValue("plugin_delete_message")
    val plugin_runtime_failed: String get() = catalog().getValue("plugin_runtime_failed")
    val plugin_tool_not_authorized: String get() = catalog().getValue("plugin_tool_not_authorized")
    val plugin_tool_not_found: String get() = catalog().getValue("plugin_tool_not_found")
    val plugin_tool_not_found_hint: String get() = catalog().getValue("plugin_tool_not_found_hint")
    val plugin_tool_permission_details: String get() = catalog().getValue("plugin_tool_permission_details")
    val plugin_tool_permission_summary: String get() = catalog().getValue("plugin_tool_permission_summary")
    val plugin_tool_permission_title: String get() = catalog().getValue("plugin_tool_permission_title")
    val plugin_tool_unavailable: String get() = catalog().getValue("plugin_tool_unavailable")
    val plugin_web_page_failed: String get() = catalog().getValue("plugin_web_page_failed")
    val plugin_web_page_retry: String get() = catalog().getValue("plugin_web_page_retry")
    val plugin_detail_title: String get() = catalog().getValue("plugin_detail_title")
    val plugin_page_unsupported: String get() = catalog().getValue("plugin_page_unsupported")
    val plugin_store_title: String get() = catalog().getValue("plugin_store_title")
    val plugin_store_refresh: String get() = catalog().getValue("plugin_store_refresh")
    val plugin_store_install_success: String get() = catalog().getValue("plugin_store_install_success")
    val plugin_update_available: String get() = catalog().getValue("plugin_update_available")
    val plugin_up_to_date: String get() = catalog().getValue("plugin_up_to_date")
    val plugin_update: String get() = catalog().getValue("plugin_update")
    val plugin_install_resolving: String get() = catalog().getValue("plugin_install_resolving")
    val plugin_install_downloading: String get() = catalog().getValue("plugin_install_downloading")
    val plugin_install_installing: String get() = catalog().getValue("plugin_install_installing")
    val plugin_install_progress: String get() = catalog().getValue("plugin_install_progress")
    val skill_detail_author: String get() = catalog().getValue("skill_detail_author")
    val skill_detail_category: String get() = catalog().getValue("skill_detail_category")
    val skill_detail_display_name: String get() = catalog().getValue("skill_detail_display_name")
    val skill_detail_download: String get() = catalog().getValue("skill_detail_download")
    val skill_detail_name: String get() = catalog().getValue("skill_detail_name")
    val skill_detail_package: String get() = catalog().getValue("skill_detail_package")
    val skill_detail_stars: String get() = catalog().getValue("skill_detail_stars")
    val skill_detail_tags: String get() = catalog().getValue("skill_detail_tags")
    val skill_detail_version: String get() = catalog().getValue("skill_detail_version")
    val skill_install_note: String get() = catalog().getValue("skill_install_note")
    val skill_market_title: String get() = catalog().getValue("skill_market_title")
    val skills_add: String get() = catalog().getValue("skills_add")
    val skills_add_from_file: String get() = catalog().getValue("skills_add_from_file")
    val skills_add_from_file_desc: String get() = catalog().getValue("skills_add_from_file_desc")
    val skills_add_from_zip: String get() = catalog().getValue("skills_add_from_zip")
    val skills_add_from_zip_desc: String get() = catalog().getValue("skills_add_from_zip_desc")
    val skills_add_manual: String get() = catalog().getValue("skills_add_manual")
    val skills_add_manual_desc: String get() = catalog().getValue("skills_add_manual_desc")
    val skills_add_sheet_title: String get() = catalog().getValue("skills_add_sheet_title")
    val skills_batch_delete_confirm: String get() = catalog().getValue("skills_batch_delete_confirm")
    val skills_batch_delete_title: String get() = catalog().getValue("skills_batch_delete_title")
    val skills_delete_confirm_message: String get() = catalog().getValue("skills_delete_confirm_message")
    val skills_delete_confirm_title: String get() = catalog().getValue("skills_delete_confirm_title")
    val skills_description: String get() = catalog().getValue("skills_description")
    val skills_description_hint: String get() = catalog().getValue("skills_description_hint")
    val skills_edit: String get() = catalog().getValue("skills_edit")
    val skills_editor_basic: String get() = catalog().getValue("skills_editor_basic")
    val skills_editor_edit: String get() = catalog().getValue("skills_editor_edit")
    val skills_editor_error_empty_instructions: String get() = catalog().getValue("skills_editor_error_empty_instructions")
    val skills_editor_error_invalid_name: String get() = catalog().getValue("skills_editor_error_invalid_name")
    val skills_editor_error_io: String get() = catalog().getValue("skills_editor_error_io")
    val skills_editor_error_name_conflict: String get() = catalog().getValue("skills_editor_error_name_conflict")
    val skills_editor_new: String get() = catalog().getValue("skills_editor_new")
    val skills_editor_scope: String get() = catalog().getValue("skills_editor_scope")
    val skills_empty: String get() = catalog().getValue("skills_empty")
    val skills_empty_hint: String get() = catalog().getValue("skills_empty_hint")
    val skills_enable: String get() = catalog().getValue("skills_enable")
    val skills_import_error_empty_content: String get() = catalog().getValue("skills_import_error_empty_content")
    val skills_import_error_invalid_archive: String get() = catalog().getValue("skills_import_error_invalid_archive")
    val skills_import_error_invalid_name: String get() = catalog().getValue("skills_import_error_invalid_name")
    val skills_import_error_io: String get() = catalog().getValue("skills_import_error_io")
    val skills_import_error_name_conflict: String get() = catalog().getValue("skills_import_error_name_conflict")
    val skills_import_error_no_skill: String get() = catalog().getValue("skills_import_error_no_skill")
    val skills_import_error_unsupported_file: String get() = catalog().getValue("skills_import_error_unsupported_file")
    val skills_import_failure_line: String get() = catalog().getValue("skills_import_failure_line")
    val skills_import_partial: String get() = catalog().getValue("skills_import_partial")
    val skills_import_result_title: String get() = catalog().getValue("skills_import_result_title")
    val skills_import_running: String get() = catalog().getValue("skills_import_running")
    val skills_import_success: String get() = catalog().getValue("skills_import_success")
    val skills_instructions: String get() = catalog().getValue("skills_instructions")
    val skills_instructions_hint: String get() = catalog().getValue("skills_instructions_hint")
    val skills_name_hint: String get() = catalog().getValue("skills_name_hint")
    val skills_no_global_skills: String get() = catalog().getValue("skills_no_global_skills")
    val skills_no_project_skills: String get() = catalog().getValue("skills_no_project_skills")
    val skills_scope_global: String get() = catalog().getValue("skills_scope_global")
    val skills_scope_project: String get() = catalog().getValue("skills_scope_project")
    val skills_summary: String get() = catalog().getValue("skills_summary")
    val slash_command_compress_desc: String get() = catalog().getValue("slash_command_compress_desc")
    val slash_command_goal_desc: String get() = catalog().getValue("slash_command_goal_desc")
    val slash_command_init_desc: String get() = catalog().getValue("slash_command_init_desc")
    val slash_command_usage_desc: String get() = catalog().getValue("slash_command_usage_desc")
    val ssh_host_key_changed_title: String get() = catalog().getValue("ssh_host_key_changed_title")
    val ssh_host_key_confirm_title: String get() = catalog().getValue("ssh_host_key_confirm_title")
    val ssh_host_key_detail: String get() = catalog().getValue("ssh_host_key_detail")
    val ssh_host_key_empty: String get() = catalog().getValue("ssh_host_key_empty")
    val ssh_host_key_fingerprint_value: String get() = catalog().getValue("ssh_host_key_fingerprint_value")
    val ssh_host_key_reject: String get() = catalog().getValue("ssh_host_key_reject")
    val ssh_host_key_remove: String get() = catalog().getValue("ssh_host_key_remove")
    val ssh_host_key_settings_desc: String get() = catalog().getValue("ssh_host_key_settings_desc")
    val ssh_host_key_settings_title: String get() = catalog().getValue("ssh_host_key_settings_title")
    val ssh_host_key_tab_fingerprints: String get() = catalog().getValue("ssh_host_key_tab_fingerprints")
    val ssh_host_key_tab_keys: String get() = catalog().getValue("ssh_host_key_tab_keys")
    val ssh_host_key_trust: String get() = catalog().getValue("ssh_host_key_trust")
    val ssh_login_key_add: String get() = catalog().getValue("ssh_login_key_add")
    val ssh_login_key_add_from_file: String get() = catalog().getValue("ssh_login_key_add_from_file")
    val ssh_login_key_add_from_file_desc: String get() = catalog().getValue("ssh_login_key_add_from_file_desc")
    val ssh_login_key_add_from_paste: String get() = catalog().getValue("ssh_login_key_add_from_paste")
    val ssh_login_key_add_from_paste_desc: String get() = catalog().getValue("ssh_login_key_add_from_paste_desc")
    val ssh_login_key_add_method_title: String get() = catalog().getValue("ssh_login_key_add_method_title")
    val ssh_login_key_content_label: String get() = catalog().getValue("ssh_login_key_content_label")
    val ssh_login_key_edit_title: String get() = catalog().getValue("ssh_login_key_edit_title")
    val ssh_login_key_empty: String get() = catalog().getValue("ssh_login_key_empty")
    val ssh_login_key_encrypted: String get() = catalog().getValue("ssh_login_key_encrypted")
    val ssh_login_key_fingerprint_label: String get() = catalog().getValue("ssh_login_key_fingerprint_label")
    val ssh_login_key_name_label: String get() = catalog().getValue("ssh_login_key_name_label")
    val ssh_login_key_passphrase_placeholder: String get() = catalog().getValue("ssh_login_key_passphrase_placeholder")
    val ssh_login_key_paste_content_placeholder: String get() = catalog().getValue("ssh_login_key_paste_content_placeholder")
    val ssh_login_key_paste_invalid: String get() = catalog().getValue("ssh_login_key_paste_invalid")
    val ssh_login_key_paste_name_placeholder: String get() = catalog().getValue("ssh_login_key_paste_name_placeholder")
    val ssh_login_key_paste_title: String get() = catalog().getValue("ssh_login_key_paste_title")
    val ssh_login_key_toggle_content: String get() = catalog().getValue("ssh_login_key_toggle_content")
    val status_connected: String get() = catalog().getValue("status_connected")
    val status_connection_failed: String get() = catalog().getValue("status_connection_failed")
    val status_disconnected: String get() = catalog().getValue("status_disconnected")
    val status_reconnecting: String get() = catalog().getValue("status_reconnecting")
    val storage_breakdown_note: String get() = catalog().getValue("storage_breakdown_note")
    val storage_category_ai_config: String get() = catalog().getValue("storage_category_ai_config")
    val storage_category_apk: String get() = catalog().getValue("storage_category_apk")
    val storage_category_caches: String get() = catalog().getValue("storage_category_caches")
    val storage_category_chat: String get() = catalog().getValue("storage_category_chat")
    val storage_category_checkpoints: String get() = catalog().getValue("storage_category_checkpoints")
    val storage_category_container: String get() = catalog().getValue("storage_category_container")
    val storage_category_container_images: String get() = catalog().getValue("storage_category_container_images")
    val storage_category_logs: String get() = catalog().getValue("storage_category_logs")
    val storage_category_other: String get() = catalog().getValue("storage_category_other")
    val storage_category_workspaces: String get() = catalog().getValue("storage_category_workspaces")
    val storage_chat_messages: String get() = catalog().getValue("storage_chat_messages")
    val storage_clean: String get() = catalog().getValue("storage_clean")
    val storage_clean_caches: String get() = catalog().getValue("storage_clean_caches")
    val storage_clean_caches_desc: String get() = catalog().getValue("storage_clean_caches_desc")
    val storage_clean_confirm_selected: String get() = catalog().getValue("storage_clean_confirm_selected")
    val storage_clean_confirm_title: String get() = catalog().getValue("storage_clean_confirm_title")
    val storage_clean_logs: String get() = catalog().getValue("storage_clean_logs")
    val storage_clean_logs_desc: String get() = catalog().getValue("storage_clean_logs_desc")
    val storage_clean_scope: String get() = catalog().getValue("storage_clean_scope")
    val storage_clean_selected: String get() = catalog().getValue("storage_clean_selected")
    val storage_clean_tool_output: String get() = catalog().getValue("storage_clean_tool_output")
    val storage_clean_tool_output_desc: String get() = catalog().getValue("storage_clean_tool_output_desc")
    val storage_clean_vision: String get() = catalog().getValue("storage_clean_vision")
    val storage_clean_vision_desc: String get() = catalog().getValue("storage_clean_vision_desc")
    val storage_device_free: String get() = catalog().getValue("storage_device_free")
    val storage_freed: String get() = catalog().getValue("storage_freed")
    val storage_logs_ai: String get() = catalog().getValue("storage_logs_ai")
    val storage_logs_app: String get() = catalog().getValue("storage_logs_app")
    val storage_refresh: String get() = catalog().getValue("storage_refresh")
    val storage_scanning: String get() = catalog().getValue("storage_scanning")
    val storage_section_breakdown: String get() = catalog().getValue("storage_section_breakdown")
    val storage_total_title: String get() = catalog().getValue("storage_total_title")
    val storage_untitled_session: String get() = catalog().getValue("storage_untitled_session")
    val storage_workspace_attachments: String get() = catalog().getValue("storage_workspace_attachments")
    val subagent_add: String get() = catalog().getValue("subagent_add")
    val subagent_all_tools: String get() = catalog().getValue("subagent_all_tools")
    val subagent_card_changed_files: String get() = catalog().getValue("subagent_card_changed_files")
    val subagent_card_collapse: String get() = catalog().getValue("subagent_card_collapse")
    val subagent_card_default_agent: String get() = catalog().getValue("subagent_card_default_agent")
    val subagent_card_expand: String get() = catalog().getValue("subagent_card_expand")
    val subagent_card_open: String get() = catalog().getValue("subagent_card_open")
    val subagent_card_status_completed: String get() = catalog().getValue("subagent_card_status_completed")
    val subagent_card_status_dispatched: String get() = catalog().getValue("subagent_card_status_dispatched")
    val subagent_card_status_failed: String get() = catalog().getValue("subagent_card_status_failed")
    val subagent_card_status_running: String get() = catalog().getValue("subagent_card_status_running")
    val subagent_card_status_stopped: String get() = catalog().getValue("subagent_card_status_stopped")
    val subagent_card_stop: String get() = catalog().getValue("subagent_card_stop")
    val subagent_description_hint: String get() = catalog().getValue("subagent_description_hint")
    val subagent_dispatch_count: String get() = catalog().getValue("subagent_dispatch_count")
    val subagent_dispatch_title: String get() = catalog().getValue("subagent_dispatch_title")
    val subagent_edit: String get() = catalog().getValue("subagent_edit")
    val subagent_editor_basic: String get() = catalog().getValue("subagent_editor_basic")
    val subagent_editor_clear: String get() = catalog().getValue("subagent_editor_clear")
    val subagent_editor_description: String get() = catalog().getValue("subagent_editor_description")
    val subagent_editor_edit: String get() = catalog().getValue("subagent_editor_edit")
    val subagent_editor_effort: String get() = catalog().getValue("subagent_editor_effort")
    val subagent_editor_error_empty_prompt: String get() = catalog().getValue("subagent_editor_error_empty_prompt")
    val subagent_editor_error_invalid_name: String get() = catalog().getValue("subagent_editor_error_invalid_name")
    val subagent_editor_error_io: String get() = catalog().getValue("subagent_editor_error_io")
    val subagent_editor_error_name_conflict: String get() = catalog().getValue("subagent_editor_error_name_conflict")
    val subagent_editor_mode: String get() = catalog().getValue("subagent_editor_mode")
    val subagent_editor_model_group: String get() = catalog().getValue("subagent_editor_model_group")
    val subagent_editor_new: String get() = catalog().getValue("subagent_editor_new")
    val subagent_editor_none: String get() = catalog().getValue("subagent_editor_none")
    val subagent_editor_scope: String get() = catalog().getValue("subagent_editor_scope")
    val subagent_editor_selected_count: String get() = catalog().getValue("subagent_editor_selected_count")
    val subagent_editor_tools_allow: String get() = catalog().getValue("subagent_editor_tools_allow")
    val subagent_editor_tools_allow_hint: String get() = catalog().getValue("subagent_editor_tools_allow_hint")
    val subagent_editor_tools_deny: String get() = catalog().getValue("subagent_editor_tools_deny")
    val subagent_editor_tools_deny_hint: String get() = catalog().getValue("subagent_editor_tools_deny_hint")
    val subagent_editor_tools_search: String get() = catalog().getValue("subagent_editor_tools_search")
    val subagent_enable: String get() = catalog().getValue("subagent_enable")
    val subagent_enable_hint: String get() = catalog().getValue("subagent_enable_hint")
    val subagent_inherit_parent: String get() = catalog().getValue("subagent_inherit_parent")
    val subagent_inject: String get() = catalog().getValue("subagent_inject")
    val subagent_inject_base: String get() = catalog().getValue("subagent_inject_base")
    val subagent_inject_base_hint: String get() = catalog().getValue("subagent_inject_base_hint")
    val subagent_inject_main_rules: String get() = catalog().getValue("subagent_inject_main_rules")
    val subagent_inject_main_rules_hint: String get() = catalog().getValue("subagent_inject_main_rules_hint")
    val subagent_inject_memory: String get() = catalog().getValue("subagent_inject_memory")
    val subagent_inject_memory_hint: String get() = catalog().getValue("subagent_inject_memory_hint")
    val subagent_inject_none: String get() = catalog().getValue("subagent_inject_none")
    val subagent_inject_project_rules: String get() = catalog().getValue("subagent_inject_project_rules")
    val subagent_inject_project_rules_hint: String get() = catalog().getValue("subagent_inject_project_rules_hint")
    val subagent_inject_skills: String get() = catalog().getValue("subagent_inject_skills")
    val subagent_inject_skills_hint: String get() = catalog().getValue("subagent_inject_skills_hint")
    val subagent_model: String get() = catalog().getValue("subagent_model")
    val subagent_name_hint: String get() = catalog().getValue("subagent_name_hint")
    val subagent_no_models: String get() = catalog().getValue("subagent_no_models")
    val subagent_prompt: String get() = catalog().getValue("subagent_prompt")
    val subagent_prompt_hint: String get() = catalog().getValue("subagent_prompt_hint")
    val subagent_scope_global: String get() = catalog().getValue("subagent_scope_global")
    val subagent_scope_project: String get() = catalog().getValue("subagent_scope_project")
    val subagent_source_file: String get() = catalog().getValue("subagent_source_file")
    val subagent_tab_sessions: String get() = catalog().getValue("subagent_tab_sessions")
    val subagent_tools: String get() = catalog().getValue("subagent_tools")
    val subagent_tools_allow: String get() = catalog().getValue("subagent_tools_allow")
    val subagent_tools_deny: String get() = catalog().getValue("subagent_tools_deny")
    val subagents_batch_delete_confirm: String get() = catalog().getValue("subagents_batch_delete_confirm")
    val subagents_batch_delete_title: String get() = catalog().getValue("subagents_batch_delete_title")
    val subagents_delete_confirm_message: String get() = catalog().getValue("subagents_delete_confirm_message")
    val subagents_delete_confirm_title: String get() = catalog().getValue("subagents_delete_confirm_title")
    val subagents_empty: String get() = catalog().getValue("subagents_empty")
    val subagents_empty_hint: String get() = catalog().getValue("subagents_empty_hint")
    val subagents_no_global: String get() = catalog().getValue("subagents_no_global")
    val subagents_no_project: String get() = catalog().getValue("subagents_no_project")
    val sync_batch_size_desc: String get() = catalog().getValue("sync_batch_size_desc")
    val sync_clear_index: String get() = catalog().getValue("sync_clear_index")
    val sync_connect_and_sync: String get() = catalog().getValue("sync_connect_and_sync")
    val sync_disconnect: String get() = catalog().getValue("sync_disconnect")
    val sync_download_all: String get() = catalog().getValue("sync_download_all")
    val sync_follow_gitignore: String get() = catalog().getValue("sync_follow_gitignore")
    val sync_gitignore_desc: String get() = catalog().getValue("sync_gitignore_desc")
    val sync_max_batch_count: String get() = catalog().getValue("sync_max_batch_count")
    val sync_max_batch_size: String get() = catalog().getValue("sync_max_batch_size")
    val sync_path_mapping: String get() = catalog().getValue("sync_path_mapping")
    val sync_save_settings: String get() = catalog().getValue("sync_save_settings")
    val sync_saved: String get() = catalog().getValue("sync_saved")
    val sync_settings_title: String get() = catalog().getValue("sync_settings_title")
    val sync_unknown_connection: String get() = catalog().getValue("sync_unknown_connection")
    val sync_upload_changes: String get() = catalog().getValue("sync_upload_changes")
    val sync_upload_full: String get() = catalog().getValue("sync_upload_full")
    val terminal_cleaning_old_container: String get() = catalog().getValue("terminal_cleaning_old_container")
    val terminal_close_tab: String get() = catalog().getValue("terminal_close_tab")
    val terminal_cursor_bar: String get() = catalog().getValue("terminal_cursor_bar")
    val terminal_cursor_block: String get() = catalog().getValue("terminal_cursor_block")
    val terminal_cursor_style_title: String get() = catalog().getValue("terminal_cursor_style_title")
    val terminal_cursor_underline: String get() = catalog().getValue("terminal_cursor_underline")
    val terminal_extracting_env: String get() = catalog().getValue("terminal_extracting_env")
    val terminal_font_builtin: String get() = catalog().getValue("terminal_font_builtin")
    val terminal_font_default: String get() = catalog().getValue("terminal_font_default")
    val terminal_font_delete: String get() = catalog().getValue("terminal_font_delete")
    val terminal_font_import: String get() = catalog().getValue("terminal_font_import")
    val terminal_font_import_failed: String get() = catalog().getValue("terminal_font_import_failed")
    val terminal_font_size_title: String get() = catalog().getValue("terminal_font_size_title")
    val terminal_font_title: String get() = catalog().getValue("terminal_font_title")
    val terminal_installing_packages: String get() = catalog().getValue("terminal_installing_packages")
    val terminal_no_open_tabs: String get() = catalog().getValue("terminal_no_open_tabs")
    val terminal_prepare_error_unknown: String get() = catalog().getValue("terminal_prepare_error_unknown")
    val terminal_preparing_env_failed: String get() = catalog().getValue("terminal_preparing_env_failed")
    val terminal_preparing_env_first_run: String get() = catalog().getValue("terminal_preparing_env_first_run")
    val terminal_preview_text: String get() = catalog().getValue("terminal_preview_text")
    val terminal_retry: String get() = catalog().getValue("terminal_retry")
    val terminal_settings_title: String get() = catalog().getValue("terminal_settings_title")
    val terminal_start_failed: String get() = catalog().getValue("terminal_start_failed")
    val terminal_theme_dracula: String get() = catalog().getValue("terminal_theme_dracula")
    val terminal_theme_follow_app: String get() = catalog().getValue("terminal_theme_follow_app")
    val terminal_theme_github_dark: String get() = catalog().getValue("terminal_theme_github_dark")
    val terminal_theme_github_light: String get() = catalog().getValue("terminal_theme_github_light")
    val terminal_theme_monokai: String get() = catalog().getValue("terminal_theme_monokai")
    val terminal_theme_one_dark: String get() = catalog().getValue("terminal_theme_one_dark")
    val terminal_theme_termius_dark: String get() = catalog().getValue("terminal_theme_termius_dark")
    val terminal_theme_title: String get() = catalog().getValue("terminal_theme_title")
    val terminal_title: String get() = catalog().getValue("terminal_title")
    val theme_auto: String get() = catalog().getValue("theme_auto")
    val theme_dark: String get() = catalog().getValue("theme_dark")
    val theme_dynamic_color: String get() = catalog().getValue("theme_dynamic_color")
    val theme_dynamic_color_desc: String get() = catalog().getValue("theme_dynamic_color_desc")
    val theme_dynamic_color_unsupported: String get() = catalog().getValue("theme_dynamic_color_unsupported")
    val theme_light: String get() = catalog().getValue("theme_light")
    val theme_mode_section: String get() = catalog().getValue("theme_mode_section")
    val theme_preset_coffee: String get() = catalog().getValue("theme_preset_coffee")
    val theme_preset_default: String get() = catalog().getValue("theme_preset_default")
    val theme_preset_forest: String get() = catalog().getValue("theme_preset_forest")
    val theme_preset_graphite: String get() = catalog().getValue("theme_preset_graphite")
    val theme_preset_section: String get() = catalog().getValue("theme_preset_section")
    val title_no_models: String get() = catalog().getValue("title_no_models")
    val todo_completed: String get() = catalog().getValue("todo_completed")
    val todo_dashboard_all_done: String get() = catalog().getValue("todo_dashboard_all_done")
    val todo_dashboard_progress: String get() = catalog().getValue("todo_dashboard_progress")
    val todo_dashboard_title: String get() = catalog().getValue("todo_dashboard_title")
    val todo_in_progress: String get() = catalog().getValue("todo_in_progress")
    val token_stats_hour_suffix: String get() = catalog().getValue("token_stats_hour_suffix")
    val tool_browser_condition_met: String get() = catalog().getValue("tool_browser_condition_met")
    val tool_browser_screenshot_failed: String get() = catalog().getValue("tool_browser_screenshot_failed")
    val tool_browser_wait_timeout: String get() = catalog().getValue("tool_browser_wait_timeout")
    val tool_changed_files_summary: String get() = catalog().getValue("tool_changed_files_summary")
    val tool_command_rule_denied: String get() = catalog().getValue("tool_command_rule_denied")
    val tool_command_rule_denied_detail: String get() = catalog().getValue("tool_command_rule_denied_detail")
    val tool_command_unanalyzable_reason: String get() = catalog().getValue("tool_command_unanalyzable_reason")
    val tool_expand_remaining: String get() = catalog().getValue("tool_expand_remaining")
    val tool_instruction: String get() = catalog().getValue("tool_instruction")
    val tool_mcp_invalid_name: String get() = catalog().getValue("tool_mcp_invalid_name")
    val tool_mcp_stdio_added: String get() = catalog().getValue("tool_mcp_stdio_added")
    val tool_memory_saved: String get() = catalog().getValue("tool_memory_saved")
    val tool_output_omitted: String get() = catalog().getValue("tool_output_omitted")
    val tool_output_saved_hint: String get() = catalog().getValue("tool_output_saved_hint")
    val tool_output_unsaved_hint: String get() = catalog().getValue("tool_output_unsaved_hint")
    val tool_permission_rule_denied: String get() = catalog().getValue("tool_permission_rule_denied")
    val tool_plan_mode_denied: String get() = catalog().getValue("tool_plan_mode_denied")
    val tool_result: String get() = catalog().getValue("tool_result")
    val tool_shizuku_confirmation_reason: String get() = catalog().getValue("tool_shizuku_confirmation_reason")
    val tool_subagent_message_delivered: String get() = catalog().getValue("tool_subagent_message_delivered")
    val tool_subagent_plan_readonly_warning: String get() = catalog().getValue("tool_subagent_plan_readonly_warning")
    val tool_subagent_provider_unresolved: String get() = catalog().getValue("tool_subagent_provider_unresolved")
    val tool_subagent_tier_fallback: String get() = catalog().getValue("tool_subagent_tier_fallback")
    val tool_subagent_tier_pinned: String get() = catalog().getValue("tool_subagent_tier_pinned")
    val tool_subagent_tier_unavailable: String get() = catalog().getValue("tool_subagent_tier_unavailable")
    val tool_vision_default_multiple: String get() = catalog().getValue("tool_vision_default_multiple")
    val tool_vision_default_single: String get() = catalog().getValue("tool_vision_default_single")
    val tool_vision_empty_response: String get() = catalog().getValue("tool_vision_empty_response")
    val tool_vision_focus: String get() = catalog().getValue("tool_vision_focus")
    val tool_vision_images_attached: String get() = catalog().getValue("tool_vision_images_attached")
    val update_channel_latest: String get() = catalog().getValue("update_channel_latest")
    val update_channel_latest_desc: String get() = catalog().getValue("update_channel_latest_desc")
    val update_channel_stable: String get() = catalog().getValue("update_channel_stable")
    val update_channel_stable_desc: String get() = catalog().getValue("update_channel_stable_desc")
    val vision_no_models: String get() = catalog().getValue("vision_no_models")
    val web_search_results_count: String get() = catalog().getValue("web_search_results_count")
    val web_search_title: String get() = catalog().getValue("web_search_title")
    val web_search_warning: String get() = catalog().getValue("web_search_warning")
    val workbench_split_handle: String get() = catalog().getValue("workbench_split_handle")
    val workspace_add_external: String get() = catalog().getValue("workspace_add_external")
    val workspace_confirm: String get() = catalog().getValue("workspace_confirm")
    val workspace_delete: String get() = catalog().getValue("workspace_delete")
    val workspace_delete_confirm: String get() = catalog().getValue("workspace_delete_confirm")
    val workspace_empty_hint: String get() = catalog().getValue("workspace_empty_hint")
    val workspace_external_added: String get() = catalog().getValue("workspace_external_added")
    val workspace_external_exists: String get() = catalog().getValue("workspace_external_exists")
    val workspace_external_invalid: String get() = catalog().getValue("workspace_external_invalid")
    val workspace_external_remove_confirm: String get() = catalog().getValue("workspace_external_remove_confirm")
    val workspace_external_remove_confirm_sessions: String get() = catalog().getValue("workspace_external_remove_confirm_sessions")
    val workspace_external_unavailable_hint: String get() = catalog().getValue("workspace_external_unavailable_hint")
    val workspace_external_unresolvable: String get() = catalog().getValue("workspace_external_unresolvable")
    val workspace_external_unsupported_path: String get() = catalog().getValue("workspace_external_unsupported_path")
    val workspace_external_unwritable: String get() = catalog().getValue("workspace_external_unwritable")
    val workspace_external_warning_continue: String get() = catalog().getValue("workspace_external_warning_continue")
    val workspace_external_warning_dont_show_again: String get() = catalog().getValue("workspace_external_warning_dont_show_again")
    val workspace_external_warning_message: String get() = catalog().getValue("workspace_external_warning_message")
    val workspace_external_warning_title: String get() = catalog().getValue("workspace_external_warning_title")
    val workspace_name_exists: String get() = catalog().getValue("workspace_name_exists")
    val workspace_name_hint: String get() = catalog().getValue("workspace_name_hint")
    val workspace_new_internal: String get() = catalog().getValue("workspace_new_internal")
    val workspace_new_workspace: String get() = catalog().getValue("workspace_new_workspace")
    val workspace_open: String get() = catalog().getValue("workspace_open")
    val workspace_remote_default_create_failed: String get() = catalog().getValue("workspace_remote_default_create_failed")
    val workspace_remote_root_create_failed: String get() = catalog().getValue("workspace_remote_root_create_failed")
    val workspace_select: String get() = catalog().getValue("workspace_select")
    val workspace_switch: String get() = catalog().getValue("workspace_switch")
    val workspace_switch_confirm: String get() = catalog().getValue("workspace_switch_confirm")
    val workspace_unavailable_current: String get() = catalog().getValue("workspace_unavailable_current")
    val workspace_unavailable_fallback: String get() = catalog().getValue("workspace_unavailable_fallback")

    val chat_upload_video: String get() = catalog().getValue("chat_upload_video")
    val chat_upload_package: String get() = catalog().getValue("chat_upload_package")
    val about_upstream: String get() = catalog().getValue("about_upstream")
    val about_releases: String get() = catalog().getValue("about_releases")
    val about_feedback: String get() = catalog().getValue("about_feedback")
    private val zh: Map<String, String> by lazy { parseTable(ZH_TABLE) }
    private val en: Map<String, String> by lazy { parseTable(EN_TABLE) }

    private fun parseTable(raw: String): Map<String, String> {
        val map = HashMap<String, String>(2048)
        for (line in raw.lines()) {
            val tab = line.indexOf('\t')
            if (tab <= 0) continue
            map[line.substring(0, tab)] = unescape(line.substring(tab + 1))
        }
        return map
    }

    private fun unescape(s: String): String {
        val sb = StringBuilder(s.length)
        var i = 0
        while (i < s.length) {
            val c = s[i]
            if (c == '\\' && i + 1 < s.length) {
                when (val n = s[i + 1]) {
                    'n' -> sb.append('\n')
                    't' -> sb.append('\t')
                    'r' -> sb.append('\r')
                    '\\' -> sb.append('\\')
                    else -> { sb.append('\\'); sb.append(n) }
                }
                i += 2
            } else {
                sb.append(c)
                i += 1
            }
        }
        return sb.toString()
    }
}

private val ZH_TABLE: String = """
app_name	VantaCode
common_tool_missing_argument	缺少参数：%1${'$'}s。
plan_mode_invalid_action	无效的 action：%1${'$'}s。支持 enter 或 exit。
plan_mode_already_active	当前已处于 PLAN（计划）模式。
plan_mode_not_active	当前不在 PLAN 模式，无法退出。
plan_mode_session_required	未关联会话，无法切换模式。
plan_mode_session_missing	会话不存在，无法切换模式。
plan_mode_goal_active	目标正在自主运行，暂不支持进入或退出 PLAN 模式。
plan_mode_entered	已进入 PLAN（计划）模式。
plan_mode_exited	已退出 PLAN，恢复为 %1${'$'}s 模式。
plan_mode_enter_action	进入 PLAN 模式
plan_mode_exit_action	退出 PLAN 模式
plan_mode_reason_unspecified	未提供理由
plan_mode_refinement_requested	用户选择继续完善方案，当前保持 PLAN 模式。等待具体反馈后再调整，暂不重复申请退出。
plan_mode_approved_build	计划已获批准，当前为 BUILD 模式。按已批准的范围实施。
plan_mode_approved_auto	计划已获批准，已恢复 AUTO 模式。按已批准的范围实施，工具调用遵守 AUTO 权限策略。
plan_mode_external_build	用户已切换到 BUILD 模式，后续操作遵守该模式权限和当前任务范围。
plan_mode_external_auto	用户已切换到 AUTO 模式，后续操作遵守该模式权限和当前任务范围。
plan_mode_request_title	切换计划模式
plan_mode_request_summary	申请%1${'$'}s
plan_mode_request_details	操作：%1${'$'}s\n\n理由：%2${'$'}s
tool_plan_mode_denied	当前为 PLAN（计划）模式，此操作未执行。该模式仅允许只读探索；需要实施时，请提交方案并申请退出 PLAN。
tool_permission_rule_denied	权限规则禁止执行此工具。
tool_command_rule_denied	权限规则禁止执行此命令。
tool_command_rule_denied_detail	权限规则禁止执行此命令。命中规则：%1${'$'}s；触发片段：%2${'$'}s；建议：改用允许的写法，或请用户在「工具授权」中调整规则后重试。
tool_command_unanalyzable_reason	命令含 %1${'$'}s 等无法静态判定的构造，只能单次放行，无法保存为始终允许
tool_shizuku_confirmation_reason	Shizuku 会以 adb shell 身份操作 Android 系统，需要逐次确认，无法保存为始终允许。
tool_vision_images_attached	已附加 %1${'$'}d 张图片，可直接查看和分析。
tool_vision_focus	关注点：%1${'$'}s
tool_vision_empty_response	识图模型未返回分析内容。
tool_vision_default_single	请描述图片中的关键信息，包括可辨认的文字、元素和布局。不清楚的内容请注明。
tool_vision_default_multiple	请按顺序描述这 %1${'$'}d 张图片中的关键信息，再比较它们的相同点与差异。不清楚的内容请注明。
tool_mcp_stdio_added	已添加本地 MCP 服务「%2${'$'}s」（%1${'$'}s）。配置将在下一次会话生效；所需运行时需另行安装。
tool_mcp_invalid_name	MCP 服务器名称只能包含 ASCII 字母、数字、下划线和连字符，当前为：%1${'$'}s。
tool_memory_saved	已保存记忆「%1${'$'}s」（%2${'$'}s）。后续会话可看到摘要，需要正文时用 read 读取。
tool_subagent_message_delivered	消息已投递。运行中的子代理会尽快收到，已结束的会继续处理消息。
tool_subagent_plan_readonly_warning	当前为 PLAN（计划）模式：子代理继承只读权限，文件修改与命令执行会被模式拦截。如确需实施，请先申请退出 PLAN 再派发。
tool_subagent_tier_pinned	该子代理定义里已写死模型（%1${'$'}s），请求的「%2${'$'}s」档被忽略。
tool_subagent_tier_fallback	请求的「%1${'$'}s」档未配置或对应提供商不可用，已改用「%2${'$'}s」档（%3${'$'}s）。
tool_subagent_tier_unavailable	请求的「%1${'$'}s」档未配置或对应提供商不可用，该子代理将继承当前会话模型。
tool_subagent_provider_unresolved	该子代理定义里的 provider「%1${'$'}s」未找到（未配置或名称不符），本次将用父会话提供商搭配定义模型「%2${'$'}s」，该组合可能无法调用。
settings_subagent_tiers_header	子代理模型档位
settings_subagent_tier_light	轻量
settings_subagent_tier_standard	均衡
settings_subagent_tier_strong	强力
settings_subagent_tier_unconfigured	未配置
subagent_dispatch_title	子代理任务
subagent_dispatch_count	已派发 %1${'$'}d 个任务
subagent_card_default_agent	子代理
subagent_card_status_dispatched	已派发
subagent_card_status_running	运行中
subagent_card_status_completed	已完成
subagent_card_status_failed	失败
subagent_card_status_stopped	已停止
subagent_card_expand	展开摘要
subagent_card_collapse	收起摘要
subagent_card_changed_files	改动 %1${'$'}d 个文件
subagent_card_open	进入会话
subagent_card_stop	停止
tool_browser_screenshot_failed	暂时无法截图。可稍后重试，或用 getText/getHtml 读取页面内容。
tool_browser_condition_met	条件已满足。
tool_browser_wait_timeout	等待超时，条件尚未满足。
tool_output_saved_hint	完整内容已保存到 %1${'$'}s，可用 readFile 分段读取。
tool_output_unsaved_hint	完整内容保存失败；当前仅保留开头和结尾，可缩小输出范围后重试。
tool_output_omitted	[已省略中间 %1${'$'}d 个字符。%2${'$'}s]
chat_context_usage_estimated	预计占可用输入预算 %1${'$'}d%%
agent_input_budget_exceeded	上下文超过当前模型的可用输入预算，请缩短输入或减少工具定义。
settings_title	设置
settings_select_section_hint	从左侧选择一个设置项
settings_providers	AI 供应商
settings_provider_editor	供应商
settings_default_models	默认模型
settings_general	偏好设置
settings_general_models	模型
settings_auto_remove_stale_models	拉取模型后自动移除无效模型
settings_auto_remove_stale_models_desc	拉取成功后，移除远端已不存在的本地模型条目
settings_general_session	会话
settings_startup_session	启动时会话
settings_startup_session_new	新开会话
settings_startup_session_new_desc	复用尚未发送过消息的空会话，否则新建
settings_startup_session_recent	打开最近会话
settings_startup_session_recent_desc	直接进入最近更新的会话
settings_delete_external_workspace_sessions	删除外部工作区聊天记录
settings_delete_external_workspace_sessions_desc	移除外部本地工作区时，一并删除该工作区的聊天记录
settings_general_network	网络
settings_first_byte_timeout	首字节超时
settings_first_byte_timeout_desc	等待首个响应块的最长时间
settings_stream_idle_timeout	数据块间隔超时
settings_stream_idle_timeout_desc	两个响应块之间的最长等待
settings_timeout_unlimited	不限制
settings_timeout_seconds	%1${'$'}d 秒
settings_timeout_input_hint	单位秒，填 0 表示不限制
settings_max_network_retries	重试次数
settings_max_network_retries_desc	网络失败后的指数退避重试次数
settings_retries_count	%1${'$'}d 次
settings_retries_input_hint	填 0 表示不重试
settings_general_input	输入
settings_enter_to_send	回车键发送消息
settings_enter_to_send_desc	关闭后回车换行，需点发送按钮发送
settings_compaction_threshold	自动压缩阈值
settings_compaction_threshold_desc	上下文占用达到窗口的该比例时自动压缩
settings_percent_value	%1${'$'}d%%
settings_compaction_threshold_input_hint	填 1-100 的整数百分比
settings_general_tools	工具
settings_sendfile_max_size	sendFile 单文件上限
settings_sendfile_max_size_desc	单个文件大小上限，超过则整批发送失败
settings_mb_value	%1${'$'}d MB
settings_sendfile_max_size_input_hint	单位 MB，填大于 0 的整数
settings_general_goal	目标（goal）
settings_goal_max_turns	最大续跑轮数
settings_goal_max_turns_desc	自主循环最多续跑多少轮，达到即停止
settings_turns_value	%1${'$'}d 轮
settings_goal_max_turns_input_hint	填大于 0 的整数轮数
settings_goal_max_tokens	token 预算
settings_goal_max_tokens_desc	自主循环累计消耗的输入+输出 token 上限
settings_tokens_value	%1${'$'}d
settings_goal_max_tokens_input_hint	填不小于 1000 的整数
settings_vision_model	识图模型
settings_compaction_model	压缩模型
settings_title_model	标题总结模型
settings_image_gen_model	生图模型
settings_mcp	MCP 服务器
settings_skills	技能
settings_container	容器与镜像
settings_log_no_files	还没有日志文件
settings_log_read_failed	读取日志失败：%1${'$'}s
settings_models_fetch_failed	拉取失败
settings_log	日志
settings_permissions	工具授权
settings_remote_servers	连接与同步
settings_shizuku	Shizuku
settings_shizuku_status_not_installed	未安装
settings_shizuku_status_not_running	服务未运行
settings_shizuku_status_denied	未授权
settings_shizuku_status_ready	已就绪
settings_shizuku_hint_not_installed	点击前往下载安装 Shizuku
settings_shizuku_hint_not_running	点击打开 Shizuku 并启动服务
settings_shizuku_hint_denied	点击申请 Shizuku 授权
settings_shizuku_hint_ready	已获授权，AI 可通过 adb shell 执行命令
settings_backup	备份与还原
settings_about	关于
settings_language	语言
settings_token_stats_title	Token 统计
settings_token_stats_period_today	今天
settings_token_stats_period_7d	近7天
settings_token_stats_period_30d	近30天
settings_token_stats_period_all	全部
settings_token_stats_total	总消耗
settings_token_stats_requests	请求次数
settings_token_stats_cost	费用
settings_token_stats_cache_hit_rate	缓存命中率
settings_token_stats_cache_read_write	读取 %1${'$'}s / 写入 %2${'$'}s
settings_token_stats_trend	消耗趋势
settings_token_stats_legend_input	输入
settings_token_stats_legend_output	输出
settings_token_stats_legend_cached	缓存
settings_token_stats_provider	供应商统计
settings_token_stats_model	模型统计
settings_token_stats_success_rate	成功率 %1${'$'}s
settings_token_stats_recent_calls	调用记录
settings_token_stats_unknown	未知
settings_token_stats_no_data	暂无数据
settings_token_stats_calls_suffix	次调用
settings_token_stats_calls_suffix2	%1${'$'}d 次调用
settings_token_stats_col_model	模型
settings_token_stats_col_reasoning_effort	思考强度
settings_token_stats_col_reasoning_wire	实际发出
settings_token_stats_col_provider	供应商
settings_token_stats_col_input	输入
settings_token_stats_col_output	输出
settings_token_stats_col_cached_read	缓存读取
settings_token_stats_col_cached_write	缓存写入
settings_token_stats_col_cache_hit_rate	命中率
settings_token_stats_col_ttfb	首字节
settings_token_stats_col_duration	耗时
settings_token_stats_col_time	时间
settings_token_stats_col_status	状态
settings_token_stats_col_cost	费用
settings_token_stats_page	第 %1${'$'}d / %2${'$'}d 页
settings_token_stats_retries	重试 %1${'$'}d 次
settings_token_stats_reset	重置
settings_token_stats_reset_title	重置 Token 统计
settings_token_stats_reset_confirm	将清空全部调用记录，此操作不可恢复。确定重置？
settings_token_stats_filter_all_providers	全部供应商
settings_token_stats_filter_all_models	全部模型
settings_token_stats_filter_clear	清除筛选
settings_token_stats_filter_provider	供应商：%1${'$'}s
settings_token_stats_filter_model	模型：%1${'$'}s
settings_token_stats_provider_keys	%1${'$'}d 个密钥
settings_token_stats_requests_unit	次请求
settings_storage	存储空间
storage_refresh	重新统计
storage_total_title	应用占用
storage_scanning	统计中…
storage_device_free	设备可用 %1${'$'}s / 共 %2${'$'}s
storage_section_breakdown	占用明细
storage_breakdown_note	点击带箭头的分类可展开明细。删会话可减小聊天记录，容器与工作区请在各自的页面重置或删除；外部目录工作区位于设备存储，未计入。
storage_freed	已释放 %1${'$'}s
storage_clean	清理
storage_clean_selected	清理选中项 · %1${'$'}s
storage_clean_confirm_title	确认清理
storage_clean_confirm_selected	将清理以下选中项，共约 %1${'$'}s。此操作不可恢复。
storage_clean_scope	仅清理本机应用缓存、日志及全局目录中的所选数据，可能影响多个工作区的历史输出读取或识图追问；不会删除聊天记录、工作区文件、容器、AI 配置或远程服务器数据。
storage_category_chat	聊天记录
storage_category_container	容器
storage_category_container_images	容器镜像包
storage_category_workspaces	工作区
storage_category_ai_config	AI 配置与产物
storage_category_checkpoints	检查点
storage_category_logs	日志
storage_category_caches	缓存
storage_category_other	其他数据
storage_category_apk	应用安装包
storage_clean_caches	缓存文件
storage_clean_caches_desc	临时文件，删除后会自动重建
storage_clean_logs	运行日志
storage_clean_logs_desc	应用日志与 AI 请求日志，删除不影响功能
storage_clean_tool_output	工具输出存档
storage_clean_tool_output_desc	AI 超长工具输出保存到磁盘的副本，删除后旧消息里的输出路径不再可读
storage_clean_vision	识图会话缓存
storage_clean_vision_desc	识图追问的历史，删除后无法继续追问旧图片
storage_untitled_session	未命名会话
storage_chat_messages	%1${'$'}d 条消息
storage_workspace_attachments	含附件 %1${'$'}s
storage_logs_app	应用日志
storage_logs_ai	AI 请求日志
language_follow_system	跟随系统
common_back	返回
common_scroll_to_bottom	回到底部
common_prev	上一页
common_next	下一页
settings_add_provider	添加供应商
settings_reconnect	重新连接
settings_add_mcp_server	添加 MCP 服务器
settings_refresh_logs	刷新日志
settings_vision_follow_chat	跟随当前聊天模型
settings_compaction_follow_chat	跟随当前聊天模型
settings_title_follow_chat	跟随当前聊天模型
settings_image_gen_unconfigured	未配置
settings_theme_title	外观主题
theme_auto	自动
theme_dark	深色
theme_light	浅色
theme_mode_section	明暗模式
theme_preset_section	配色方案
theme_preset_default	默认蓝
theme_preset_coffee	咖啡
theme_preset_graphite	石墨
theme_preset_forest	森林
theme_dynamic_color	动态取色
theme_dynamic_color_desc	从系统壁纸提取配色，开启后配色方案不再生效
theme_dynamic_color_unsupported	需要 Android 12 及以上系统
settings_background_image	背景图
settings_background_image_none	未设置
settings_background_image_alpha	透明度
settings_background_image_pick	选择图片
settings_background_image_remove	移除背景
team_member_add	添加成员
team_editor_add_agent	从子代理添加
team_editor_add_model	从模型添加
settings_background_kind_gif	GIF 背景
settings_background_kind_video	视频背景
settings_teams	团队
chat_open_teams	团队
teams_new	新建团队
teams_empty	还没有团队
teams_empty_hint	把几个子代理编成一队，让他们一起讨论
teams_list_header	已创建的团队
teams_member_count	%1${'$'}d 名成员
team_editor_new	新建团队
team_editor_edit	编辑团队
team_editor_basic	基本信息
team_editor_owner	队长
team_editor_owner_name	你的显示名称
team_editor_pick_avatar	点击选头像
team_editor_members	成员
team_editor_members_hint	成员就是启用的子代理；也可以钉一个模型和一句话职责
team_editor_rename_member	成员名称
team_editor_remove_member	移出团队
team_editor_autocompose	自动组队
team_editor_autocompose_action	让 AI 安排角色
team_editor_autocompose_placeholder	例如：前端方向，必须有人审代码（可选）
team_editor_autocomposing	正在编制…
team_editor_no_agents	还没有可用的子代理，先去子代理里创建
team_editor_no_models	还没有配置模型，先去 AI 供应商里添加
team_editor_no_members	还没有成员。从子代理或模型里拉几个人进来
team_member_lead	队长
team_member_prompt_hint	一句话说清这个成员负责什么
team_name_hint	比如：前端小队
team_description_hint	这个团队擅长什么；主 agent 据此决定何时派它
team_strategy_hint	成员之间怎么分工、怎么交接、先做什么后做什么
team_error_invalid_name	团队名不合法，或和已有团队重名
team_error_name_conflict	已存在同名团队
team_error_member_limit	成员太多了（最多 %1${'$'}d 个）
team_error_no_members	至少选一个成员
team_delete	删除团队
team_delete_confirm	删除团队「%1${'$'}s」？聊天记录会一并删除。
team_chat_title	群聊
team_chat_input_hint	对大家说点什么
team_chat_send	发送
team_chat_stop	停止
team_chat_thinking	成员正在讨论…
team_chat_empty	还没人说话
team_chat_empty_hint	%1${'$'}s，你先开个头，成员会接着聊
team_chat_clear	清空聊天记录
team_chat_clear_confirm	清空这个团队的全部群聊记录？
provider_cache_anthropic_title	Anthropic 缓存断点
provider_cache_anthropic_subtitle	为 system/工具/消息前缀打显式缓存断点，降低重复输入成本
provider_cache_openai_chat_title	OpenAI 兼容缓存键
provider_cache_openai_chat_subtitle	为请求携带 prompt_cache_key 缓存键
provider_section_dashboard	自定义面板
provider_dashboard_script	面板脚本
provider_dashboard_script_placeholder	例如 demo_balance.py
provider_dashboard_select_script	选择脚本
provider_dashboard_script_desc	执行并预览面板
provider_dashboard_test_btn	运行测试
provider_dashboard_run_test	运行测试
provider_dashboard_preview_title	面板预览
provider_test_detail_title	测试调试信息
provider_test_req_url	请求 URL
provider_test_req_headers	请求头
provider_test_req_body	请求体
provider_test_resp_headers	响应头
provider_test_resp_body	响应体
provider_test_error_detail	错误堆栈
provider_dashboard_test_failed	执行失败
provider_dashboard_no_scripts	~/.aicode/scripts 目录下暂无脚本
provider_dashboard_select_title	选择面板脚本
provider_dashboard_clear_script	不使用面板脚本 (清空)
provider_dashboard_loading	正在加载面板…
provider_dashboard_raw_output	原始输出
provider_script_params_title	脚本参数
provider_script_params_desc	以 AICODE_KEY_ 注入面板脚本环境变量
provider_script_params_empty	暂无参数，点击下方添加
provider_script_param_key	参数名 (KEY)
provider_script_param_key_hint	例如 ACCOUNT_ID
provider_script_param_value	参数值
provider_script_param_value_hint	如 acc_12345
provider_script_param_add	添加参数
provider_script_param_remove	删除参数
provider_script_param_manage	管理
provider_script_params_count	已配置 %1${'$'}d 个参数
provider_add_sheet_title	添加供应商
provider_custom	自定义供应商
provider_preset_loading	正在加载预设库…
provider_preset_empty	未找到匹配的预设
provider_register_now	立即注册
settings_keepalive_title	后台运行保活
settings_keepalive_subtitle	显示前台通知，防止后台被杀
settings_screen_on_title	屏幕常亮
settings_screen_on_subtitle	App 在前台时不自动息屏
settings_network_guide_title	后台断网排查
settings_network_guide_subtitle	切到后台后断网？按这里逐项检查
settings_network_guide_intro	若切到后台后出现「网络连接断开」，请按以下顺序设置：
settings_network_guide_step_keepalive	1. 打开上方「后台运行保活」，并保留通知栏的常驻通知
settings_network_guide_step_lock	2. 在多任务界面给本应用加锁，不要直接划掉
settings_network_guide_step_battery	3. 在系统设置里把本应用的省电策略设为「无限制 / 不优化」，并允许自启动
settings_network_guide_brand_huawei	华为 / 荣耀：应用启动管理 → 手动管理 → 允许自启动、关联启动、后台活动
settings_network_guide_brand_xiaomi	小米 / Redmi：省电与电池 → 应用智能省电 → 无限制
settings_network_guide_brand_oppo	OPPO / 一加 / realme：应用管理 → 电池使用 → 允许后台活动；电池 → 更多设置 → 优化电池使用 → 不优化
settings_network_guide_brand_vivo	vivo / iQOO：电池 → 后台耗电管理 → 允许后台高耗电；应用与权限 → 自启动
settings_network_guide_brand_samsung	三星：电池和设备维护 → 电池 → 后台使用限制 → 加入「从不休眠」；应用 → 电池 → 不受限制
settings_network_guide_more	更多机型与详细说明见官网文档
settings_agent_sound_title	Agent 完成通知
settings_agent_sound_subtitle	退到后台时 Agent 完成弹出通知
settings_live_notification_title	实时通知
settings_live_notification_subtitle	AI 在后台工作时展示实时进度通知
settings_approval_notification_title	授权审批通知
settings_approval_notification_subtitle	AI 请求授权、提问或等待计划审批时弹出通知
agent_complete_notification_title	任务完成
agent_complete_notification_body	点击返回查看
agent_complete_notification_background_body	后台任务「%1${'$'}s」已完成
agent_complete_notification_background_multi_body	%1${'$'}d 个后台任务已完成
notification_channel_terminal_service	终端服务
notification_channel_terminal_service_desc	后台终端任务通知
notification_keepalive_persistent_sessions	后台保活 · 终端任务: %1${'$'}d
notification_keepalive_running_sessions	运行中的终端任务: %1${'$'}d
notification_keepalive_enabled	后台保活已开启
notification_channel_agent_complete	Agent 完成通知
notification_channel_agent_complete_desc	Agent 在后台完成任务时弹出的通知
notification_channel_agent_live	AI 实时进度
notification_channel_agent_live_desc	AI 在后台工作时展示当前进度的通知
notification_agent_live_title_multi	%1${'$'}d 个任务进行中
notification_channel_agent_approval	授权审批通知
notification_channel_agent_approval_desc	AI 在后台请求授权、提问或等待计划审批时弹出的通知
notification_approval_title	AI 等待你的确认
notification_approval_multi_title	%1${'$'}d 个任务等待确认
notification_approval_needed	需要审批
notification_approval_label_permission	需要授权
notification_approval_label_question	需要回答
notification_approval_label_plan	等待计划审批
notification_approval_action_allow	允许
notification_approval_action_reject	拒绝
notification_approval_action_approve_plan	批准
settings_app_permissions	软件权限
settings_permission_apk_install	安装未知应用
settings_permission_apk_install_desc	允许安装 APK 安装包
settings_permission_storage	访问存储空间
settings_permission_storage_desc	读写工作区文件与容器镜像
settings_permission_battery	忽略电池优化
settings_permission_battery_desc	允许后台持续运行，减少被杀
settings_autostart_title	自启动管理
settings_autostart_subtitle	允许系统在后台自动拉起应用
settings_permission_granted	已允许
settings_permission_denied	未允许
settings_permission_notification_title	需要通知权限
settings_permission_notification_denied_desc	后台保活与 Agent 完成通知需要通知权限才能生效，当前无法弹出系统授权框，请在系统设置中手动开启
settings_permission_storage_denied_desc	存储权限已被拒绝且无法再次申请，请在系统设置中手动开启
settings_category_ai	AI 配置
settings_category_environment	运行环境
settings_category_permissions	权限与后台
settings_category_general	通用设置
settings_category_data	数据与诊断
settings_category_help	帮助与关于
settings_category_background	后台运行
settings_category_system_permissions	系统权限
settings_user_guide	使用手册
common_ai_providers	AI 供应商
common_save	保存
common_done	完成
common_copy	复制
common_copy_success	已复制到剪贴板
common_cut	剪切
common_paste	粘贴
common_allow	允许
common_close	关闭
common_clear	清除
common_more	更多
workbench_split_handle	拖动调整分栏宽度
common_other	其他
common_switch	切换
common_create	创建
common_delete	删除
common_cancel	取消
common_retry	重试
common_confirm	确定
common_go_to_settings	去设置
common_name	名称
common_image_preview	图片预览
common_image_load_failed	图片加载失败，文件可能已被移动或删除
common_expand	展开
common_workspace	工作区
common_tool	工具
common_file	文件
common_collapse	折叠
common_collapse_action	收起
common_reset	重置
chat_new_session	新建会话
common_new_tab	新建标签
common_show	显示
common_local	本地
common_model	模型
common_label_with_api	%1${'$'}s（%2${'$'}s）
common_add	添加
credential_add	添加凭据
common_username	用户名
common_got_it	知道了
common_edit	编辑
common_enabled	已启用
common_disabled	已禁用
credential_token	访问令牌（Token / PAT）
credential_auto_inject_title	自动注入凭证
credential_auto_inject_hint	把应用中保存的 git 凭据同步到远程服务器，仅对当前工作区根目录下的仓库生效，不影响你在服务器上手动使用 git
credential_auto_inject_prompt_title	自动注入 git 凭证？
credential_auto_inject_prompt_body	是否让 AiCode 把应用中保存的 git 凭据同步到该远程服务器？仅对当前工作区根目录下的仓库生效，不会改动服务器的全局 git 配置。
credential_auto_inject_enable	开启
common_rename	重命名
common_hide	隐藏
log_files	日志文件
log_mcp_prefix	MCP 日志：%1${'$'}s
log_no_match	当前筛选条件下没有日志
log_search_hint	搜索日志
log_search_results	匹配 %1${'$'}s 行
log_show_last_lines	显示最后 %1${'$'}s/%2${'$'}s 行
log_show_lines	显示 %1${'$'}s 行
log_copy	复制
log_copied	已复制
log_clear_filter	清除筛选
log_copy_menu_title	复制日志
log_copy_line	复制本条日志
log_copy_with_stack	复制本条及堆栈
log_copy_message	复制消息内容
log_follow_latest	实时滚动
sync_unknown_connection	未知连接
chat_attachment_prefix	附件：
chat_attachment_max	最多可同时发送 %1${'$'}s 个附件
chat_attachment_uploaded	已上传 %1${'$'}s 个附件
chat_attachment_uploaded_partial	已上传 %1${'$'}s 个附件，部分附件未加入
chat_image_too_large	图片超过 %1${'$'}s，请压缩后重试
chat_attachment_uploading	正在上传 %1${'$'}d 个文件…
chat_uploaded_to	已上传到 %1${'$'}s
chat_select_workspace_first	请先选择工作区
chat_read_file_failed	无法读取所选文件
chat_camera_file_failed	无法创建拍照临时文件
chat_workspace_unavailable	工作区不可用
chat_upload_failed	上传失败
chat_unsupported_image_type	无法识别图片类型，仅支持 jpg/png/gif/webp
chat_new_session_btn	新会话
chat_open_sidebar	打开侧边栏
chat_add_attachment	添加附件
chat_take_photo	拍照
chat_reasoning_effort	思考强度
chat_reasoning_effort_none	不思考
chat_reasoning_effort_minimal	极低
chat_reasoning_effort_low	低
chat_reasoning_effort_medium	中
chat_reasoning_effort_high	高
chat_reasoning_effort_xhigh	极高
chat_reasoning_effort_max	最高
chat_reasoning_toggle	思考
chat_reasoning_off_short	不思考
chat_reasoning_default_tag	默认
chat_reasoning_effective_tag	生效
chat_reasoning_effective	实际生效：%1${'$'}s
chat_reasoning_unknown_hint	未识别到该模型支持的档位，所选档位可能不生效
chat_reasoning_cannot_disable	该模型无法关闭思考，已改用最低档
chat_reasoning_endpoint_weak	当前端点可能忽略「关闭」，改用该厂商的新版端点（Responses）才能真关闭
provider_model_section_reasoning	思考档位
provider_model_reasoning_hint	只勾选该模型真实支持的档位；全部不勾则由内置能力表自动判断
provider_model_reasoning_can_disable	可以关闭思考
chat_open_file_missing	文件不存在或已被移动
chat_open_file_unshareable	文件所在位置无法分享给其它应用
chat_open_file_no_app	没有可打开该文件的应用
chat_open_apk_permission	安装应用需要授权，请在跳转的设置中允许来自此来源的应用
chat_attachment_preview	预览 %1${'$'}s
chat_attachment_open	打开 %1${'$'}s
chat_no_model_selected	未选择模型
chat_open_git	打开 Git
chat_open_terminal	打开终端
chat_open_browser	打开浏览器
chat_ssh_connected	SSH：已连接
chat_ssh_connecting	SSH：连接中…
chat_ssh_failed	SSH：连接失败
chat_ssh_disconnected	SSH：未连接
chat_connecting_remote	正在连接远程服务器…
chat_remote_connect_failed	远程服务器连接失败，请检查设置
chat_no_remote_connection	未连接远程服务器
chat_placeholder	有什么可以帮你的？
chat_input_hint	描述你的需求，我来帮你编写、修改或解释代码
agent_stopped_by_user	已被用户停止
agent_tool_executing	%1${'$'}s 执行中…
agent_stop_refusal	模型基于内容安全策略拒绝了本次回答。
agent_stop_context_exceeded	本次输入超出模型上下文窗口，请先压缩上下文或新建会话。
agent_context_no_compaction	当前上下文无需压缩（消息过少）
agent_compaction_failed	压缩失败：%1${'$'}s
agent_model_not_selected	未选择
about_version	版本
about_github_repo	GitHub 仓库
about_license	开源许可证
about_description	VantaCode · 电脑端的通用 AI Coding Agent · 支持本地与远程 SSH
about_check_update	检查更新
about_checking_update	正在向 GitHub 查询最新版本…
about_up_to_date	已经是最新版本
about_up_to_date_detail	当前版本 v%1${'$'}s，已是 GitHub 上最新的 Release。
about_new_version_found	发现新版本
about_download	前往下载
about_later	稍后
about_check_failed	检查失败
about_ok	确定
about_qq_group	QQ交流群
about_qq_group_copied	群号已复制
about_parse_version_failed	无法解析最新版本号
about_network_error	网络错误
about_auto_check_update	自动检查更新
about_auto_check_update_desc	打开 App 时自动检测新版本
about_update_channel	更新通道
update_channel_stable	稳定版
update_channel_stable_desc	仅检测正式版更新
update_channel_latest	预览版
update_channel_latest_desc	除正式版外，还检测预览版（RC）更新
about_no_changelog	暂无更新说明
about_update_mirror	下载镜像源
about_update_mirror_desc	加速更新包下载；留空表示直连 GitHub
about_update_mirror_preset_cdn	gh-proxy CDN（默认）
about_update_mirror_preset_v6	gh-proxy 优选节点
about_update_mirror_preset_direct	直连 GitHub
about_update_mirror_invalid	地址需以 http:// 或 https:// 开头
about_update_mirror_default	恢复默认
about_update_download_install	下载并安装
about_update_install	安装
about_update_browser	浏览器下载
about_update_background	后台继续
about_update_downloading	正在下载 %1${'$'}d%%
about_update_download_size	%1${'$'}s / %2${'$'}s
about_update_apk_ready	安装包已就绪，点「安装」完成更新
about_update_need_permission	需先允许「安装未知应用」，授权后回来点「安装」即可
about_update_download_failed	下载失败
about_update_download_failed_hint	可在「设置 → 关于 → 下载镜像源」更换镜像后重试
about_update_retry	重试
about_update_verify_not_apk	下载到的文件不是有效安装包
about_update_verify_package	安装包与当前应用不匹配
about_update_verify_signature	安装包签名与当前应用不一致，请改用浏览器下载
about_update_verify_not_newer	安装包版本不高于当前版本
about_update_install_failed	没有可用的安装程序
agreement_title	欢迎使用 AiCode
agreement_terms	用户协议
agreement_privacy	隐私政策
agreement_notice_prefix	使用前，请阅读并同意
agreement_terms_link	《用户协议》
agreement_notice_conjunction	和
agreement_privacy_link	《隐私政策》
agreement_notice_suffix	，了解使用规则与数据处理方式。\n\n点击“同意并继续”表示你已阅读并同意上述两份文件。
agreement_accept	同意并继续
agreement_decline	不同意
agreement_terms_url	https://aicode.murk.top/terms
agreement_privacy_url	https://aicode.murk.top/privacy
ask_confirm_title	AI 需要你的确认
ask_supplement	补充
ask_confirm	确认
ask_custom_answer	输入自定义答案
ask_input_hint	请输入…
backup_exported	备份已导出
backup_write_failed	写入失败：%1${'$'}s
backup_read_failed	读取备份文件失败
backup_export_title	导出备份
backup_export_subtitle	选择要导出的数据，可设置口令加密
backup_import_title	导入备份
backup_import_subtitle	从备份文件还原数据
backup_set_password	设置导出口令
backup_password_hint	留空则不加密，输出明文 tar.gz；填写则用口令加密（AES-GCM）
backup_export_btn	导出
backup_password_input	输入备份口令
backup_password_optional_hint	若备份未加密可留空
backup_import_btn	导入
backup_operation_failed	操作失败
backup_import_done	导入完成
backup_actions	操作
backup_select_data	选择导出数据
backup_data_remote	连接与同步
backup_data_chat_history	聊天记录
backup_data_mcp	MCP 服务器
backup_data_permissions	授权规则
backup_data_app_settings	应用设置
backup_data_workspace	工作区文件
backup_workspace_git_hint	工作区文件建议通过 Git 提交备份，导出备份仅作备用
backup_password_label	口令（可选）
backup_processing	处理中
backup_processing_data	正在处理数据…
backup_restored_data	已还原数据：
backup_stat_providers	· AI 供应商 %1${'$'}s 个
backup_stat_remote_connections	· 远程连接 %1${'$'}s 个
backup_stat_remote_mounts	· 远程挂载 %1${'$'}s 个
backup_stat_chat_sessions	· 聊天会话 %1${'$'}s 个
backup_stat_chat_messages	· 聊天消息 %1${'$'}s 条
backup_stat_todo_items	· 待办项 %1${'$'}s 条
backup_stat_mcp_servers	· MCP 服务器 %1${'$'}s 个
backup_stat_permission_rules	· 全局授权规则 %1${'$'}s 条
backup_stat_workspace_files	· 工作区文件 %1${'$'}s 个
backup_settings_covered	主题、保活、日志、识图、同步偏好已覆盖
backup_restore_workspaces_title	恢复工作区
backup_restore_workspaces_warning	将直接覆盖本地同名工作区中的文件，且无法撤销，请谨慎勾选
backup_restore_workspaces_select_all	全选
backup_restore_workspaces_files	%1${'$'}d 个文件
backup_restore_workspaces_confirm	确认恢复
backup_export_failed	导出失败
backup_wrong_password	备份口令错误，或加密备份文件已损坏
backup_import_failed	导入失败
backup_net_section	网络备份
backup_tab_local	本地备份
backup_net_targets	备份目标
backup_net_add_target	添加备份目标
backup_net_edit_target	编辑备份目标
backup_net_no_target	尚未添加网络备份目标
backup_net_no_target_hint	点击右上角 + 添加 WebDAV 或 S3 备份目标
backup_net_upload	上传到云端
backup_net_upload_short	上传
backup_net_upload_password_hint	备份将上传到云端，为避免 API Key、SSH 凭据等明文泄露，必须设置口令加密（AES-GCM）
backup_net_upload_password_required	云端备份必须设置加密口令
backup_net_upload_subtitle	导出当前备份并上传到所选目标
backup_net_restore	从云端恢复
backup_net_restore_subtitle	从所选目标下载备份并还原
backup_net_test_connection	测试连接
backup_net_test_success	连接成功
backup_net_test_failed	连接失败
backup_net_upload_failed	上传失败
backup_net_list_failed	读取云端备份失败
backup_net_delete_failed	删除云端备份失败
backup_net_upload_done	已上传到 %1${'$'}s
backup_net_type_webdav	WebDAV
backup_net_type_s3	对象存储 (S3)
backup_net_name	名称
backup_net_endpoint	服务器地址
backup_net_endpoint_hint	如 https://dav.example.com/dav
backup_net_base_path	远端目录
backup_net_base_path_hint	WebDAV 目录或 S3 对象前缀
backup_net_username	用户名
backup_net_password	密码
backup_net_access_key	Access Key
backup_net_secret_key	Secret Key
backup_net_session_token	Session Token（可选）
backup_net_bucket	存储桶
backup_net_region	区域
backup_net_path_style	路径风格寻址
backup_net_remote_list_title	云端备份
backup_net_remote_empty	云端暂无备份
backup_net_remote_restore	恢复
backup_net_remote_delete	删除
backup_net_remote_delete_confirm	删除云端备份 %1${'$'}s？该操作不可撤销。
backup_net_loading	正在读取云端备份…
backup_net_uploading	正在上传备份…
backup_net_testing	正在测试连接…
backup_net_delete_target_confirm	删除备份目标 %1${'$'}s？
chat_history	历史记录
chat_no_sessions_hint	暂无会话
chat_search_hint	搜索聊天记录
chat_search_no_result	未找到匹配的聊天记录
chat_search_clear	清除
session_group_today	今天
session_group_yesterday	昨天
session_group_last_7_days	7天内
session_group_last_30_days	30天内
session_group_pinned	置顶
session_group_month_format	yyyy年M月
chat_settings	设置
chat_pin_session	置顶
chat_unpin_session	取消置顶
chat_delete_session	删除会话
chat_delete_session_confirm	确定删除「%1${'$'}s」？该会话的所有消息都将被清除，且无法恢复。
chat_batch_select	批量选择
chat_batch_delete_title	删除所选会话
chat_batch_delete_confirm	确定删除选中的 %1${'$'}d 个会话？包含的所有消息都将被清除，且无法恢复。
chat_sessions_selected_count	已选 %1${'$'}d 个会话
subagent_tab_sessions	会话
drawer_tab_files	文件
chat_collapse_sidebar	收起侧栏
drawer_expand_subagents	展开子代理
drawer_collapse_subagents	收起子代理
file_browser_up	上一级
file_browser_empty	空目录
file_browser_error	读取目录失败
file_browser_refresh	刷新
file_browser_set_as_workspace	设为工作区
file_browser_expand	展开
file_browser_collapse	折叠
file_browser_new_file	新建文件
file_browser_new_folder	新建文件夹
file_browser_name_label	名称
file_browser_delete_file_confirm	确定删除文件「%1${'$'}s」？
file_browser_delete_folder_confirm	确定删除文件夹「%1${'$'}s」及其全部内容？
file_browser_create_failed	创建失败，可能已存在同名项或无写入权限
file_browser_rename_failed	重命名失败，可能已存在同名项
file_browser_delete_failed	删除失败
file_browser_paste_here	粘贴到此处
file_browser_paste_conflict_title	同名文件已存在
file_browser_paste_conflict_message	目标位置已存在同名项目「%1${'$'}s」，是否覆盖？
common_overwrite	覆盖
file_browser_clipboard_cut	已剪切
file_browser_clipboard_copy	已复制
editor_undo	撤销
editor_redo	重做
editor_save_success	已保存
editor_save_failed	保存失败
editor_unsaved_title	未保存的修改
editor_unsaved_message	当前文件有未保存的修改，是否保存？
editor_discard	放弃
editor_save_and_exit	保存并退出
editor_save_and_switch	保存并切换
editor_dont_save	不保存
editor_md_preview	预览 Markdown
editor_md_show_source	显示源码
editor_indent	缩进
editor_cursor_left	光标左移
editor_cursor_right	光标右移
editor_settings	编辑器设置
editor_font_size	字体大小
editor_word_wrap	自动换行
editor_show_indent_guide	显示缩进参考线
editor_show_wrap_arrow	显示自动换行箭头
editor_show_whitespace	显示空白符号
editor_font_size_value	%1${'$'}d sp
editor_file_too_large	文件过大（%1${'$'}d MB），暂不支持打开
editor_load_failed	文件打开失败
editor_binary_unsupported	二进制文件，暂不支持打开
chat_rename_session	重命名会话
chat_session_name	会话名
chat_export_session	导出会话
chat_export_session_done	会话已导出
chat_export_session_failed	导出失败
chat_input_placeholder	输入消息，与 AI 对话…
chat_queue_hint	AI 正在处理中，回车发送将自动排队
chat_input_expand	展开输入框
chat_mention_loading	正在加载文件列表…
chat_mention_unavailable	容器未就绪，无法搜索文件
chat_mention_error	加载文件列表失败
chat_mention_empty	没有匹配的文件
chat_input_fullscreen_title	全屏输入
chat_queue_title	待发送队列（%1${'$'}d）
chat_queue_remove	移除排队消息
chat_queue_edit	编辑排队消息
chat_queue_interject	立即插入给 AI
chat_queue_drag	拖拽排序
chat_upload_file	上传文件
chat_upload_image	上传图片
chat_remove_attachment	移除附件
chat_no_models_hint	暂无可用模型，请在「AI 供应商」中添加
chat_stop	停止
chat_send	发送
chat_perm_single_use_desc	含命令替换/管道/重定向，无法记住，仅可单次放行
chat_perm_always_tool_desc	「始终允许」将在本项目记住：该工具的全部调用
chat_perm_always_prefix	「始终允许」将在本项目记住：
chat_perm_deny	拒绝
chat_perm_always_allow	始终允许
chat_perm_session_label	会话「%1${'$'}s」
chat_plan_completed	计划已完成
chat_continue_feedback	继续反馈
chat_approve_and_implement	批准并实施
checkpoint_rewind_title	检查点与撤销
checkpoint_target_prompt	目标消息：%1${'$'}s
checkpoint_restore_code_and_conversation	恢复代码与对话
checkpoint_restore_code_and_conversation_desc	将代码与对话均回滚至此检查点之前
checkpoint_restore_conversation	仅恢复对话
checkpoint_restore_conversation_desc	保持当前代码，将对话截断回滚至此点
checkpoint_restore_code	仅恢复代码
checkpoint_restore_code_desc	恢复代码至此点之前的快照，保持对话历史
checkpoint_skipped_single	已跳过 1 个文件：它在快照之后被其他会话或你改过，未回滚以保留这些改动\n%1${'$'}s
checkpoint_skipped_multiple	已跳过 %1${'$'}d 个文件：它们在快照之后被改动，未回滚以保留这些改动\n%2${'$'}s
editor_save_conflict_title	文件已被修改
editor_save_conflict_message	该文件在你打开之后已被 AI 或其他程序修改。直接保存会覆盖对方的改动。
editor_save_conflict_diff	- 磁盘当前内容\n+ 你的编辑\n\n%1${'$'}s
editor_save_conflict_overwrite	覆盖磁盘
editor_save_conflict_discard	放弃我的修改
editor_save_conflict_cancel	取消
editor_save_conflict_discarded	已放弃修改并重新载入磁盘内容
chat_copied	已复制
chat_copy	复制
chat_task_duration	本轮总耗时 %1${'$'}s
chat_cache_hit_rate	缓存命中率 %1${'$'}s
chat_status_generating	正在生成
chat_status_thinking	正在思考
chat_status_calling_tool	正在调用工具
chat_status_running_command	正在执行命令
chat_status_editing_file	正在编辑文件
chat_status_reading_file	正在读取文件
chat_status_viewing_image	正在查看图片
chat_status_searching_web	正在联网搜索
chat_status_browsing_web	正在操作浏览器
chat_status_asking_user	正在询问用户
chat_status_generating_image	正在生成图片
chat_status_updating_todo	正在整理待办
chat_status_starting_subagent	正在启动子代理
chat_status_reading_memory	正在读取记忆
chat_tool_running	工具执行中
chat_tool_succeeded	工具执行成功
chat_tool_failed	工具执行失败
chat_tool_calls_count	%1${'$'}d 次工具调用
chat_turn_running	执行中
chat_turn_completed	已完成 %1${'$'}s
chat_turn_completed_plain	已完成
chat_context_usage	上下文已用 %1${'$'}d%%
chat_bg_command_done	后台命令已完成
chat_bg_commands_done	%1${'$'}d 个后台任务已完成
chat_bg_commands_partial_failed	%1${'$'}d 个后台任务结束，%2${'$'}d 个失败
chat_context_compressed	上下文已压缩
chat_compaction_failed	上下文压缩失败
chat_compressing_context	正在压缩上下文
chat_retrying	正在重试 %1${'$'}s/%2${'$'}s
chat_key_switched	已切换到第 %1${'$'}d/%2${'$'}d 个 Key
chat_goal_label	目标·%1${'$'}s
chat_goal_status_active	进行中
chat_goal_status_paused	已暂停
chat_goal_status_completed	已完成
chat_goal_status_blocked	受阻
chat_goal_status_exhausted	预算耗尽
chat_goal_pause	暂停目标
chat_goal_resume	恢复目标
chat_goal_clear	清除目标
chat_error_title	请求失败
network_error_dns	DNS 解析失败：域名没有解析到可用地址，请检查网络、Wi-Fi 的 DNS 或代理设置
network_error_timeout	请求超时：网络较慢或服务未响应
network_error_connect	连接被拒绝：请检查 Base URL 与端口
network_error_ssl	SSL 握手失败：请检查证书或代理设置
common_error_unknown	未知错误
slash_command_compress_desc	手动触发当前会话的上下文压缩
slash_command_init_desc	分析当前工作区代码库，生成或改进项目规则 AGENTS.md
slash_command_usage_desc	查看今日与累计的调用次数、token 用量与预估费用
slash_command_goal_desc	设定会话持续目标，AI 自主循环推进至达成或预算耗尽
retry_error_rate_limit	速率限制
retry_error_server_overloaded	服务器负载过高
retry_error_server	服务端错误
retry_error_network	网络连接断开
retry_error_timeout	连接超时
retry_error_connection_refused	连接被拒绝
retry_error_dns_failed	DNS 解析失败
retry_error_connection_reset	连接中断
retry_error_ssl	SSL 握手失败
retry_error_unknown	网络异常
retry_error_with_code	%1${'$'}s (%2${'$'}d)
chat_thinking_process	思考过程
chat_thinking_running_time	正在思考（耗时 %1${'$'}s 秒）
chat_thinking_done_time	思考完成（耗时 %1${'$'}s 秒）
chat_thinking_done	思考完成
container_reset	重置
container_add_image	添加镜像
container_remote_ssh	远程 SSH
container_custom_image	自定义镜像
container_delete_config	删除镜像配置
container_delete_confirm	确定删除「%1${'$'}s」？%2${'$'}s
container_rootfs_will_be_cleared	其 rootfs 目录会被一并清除。
container_builtin_restorable	内置镜像删除后可在空列表恢复。
container_switch_image	切换容器与镜像
container_switch_confirm	切换到「%1${'$'}s」后，当前正在运行的 AI 会话将被停止、终端标签将被关闭。是否继续切换？
container_reset_title	重置容器
container_resetting_title	正在清理容器数据
container_resetting_message	正在删除「%1${'$'}s」的数据…\n已清理 %2${'$'}s 项
container_reset_confirm	将删除「%1${'$'}s」的 rootfs 目录（含已安装的工具与配置），下次使用时重新解压并初始化。是否继续？
container_builtin_badge	内置
container_custom_badge	自定义
container_ssh_badge	SSH
container_empty	暂无容器与镜像
container_empty_desc	添加一个自定义镜像，或恢复内置 Alpine。
container_restore_builtin	恢复内置 Alpine
container_set_as_default	设为默认容器
container_default_badge	默认
container_default_desc	远程工作区模式下，本地 MCP 服务运行在此容器上
container_shell_desc	shell: %1${'$'}s
container_edit_image	编辑镜像
container_local_image	本地镜像
container_shell_path	Shell
container_extra_bindings	挂载
container_extra_proot_args	proot 参数
container_importing	正在复制镜像文件…
container_not_ready_hint	容器尚未初始化，请先进入终端页面完成初始化后再执行命令
container_file_selected	已选择文件
container_select_image_file	选择 tar.gz / tgz / tar.xz / txz 镜像文件
container_sort_long_press	长按拖动排序
container_mount_local	本地目录
container_mount_container	容器目录
container_no_bindings	无挂载
container_add_binding	添加挂载
container_arg_value	参数值
container_no_args	无参数
container_add_arg	添加参数
container_env_vars	环境变量
container_env_name	变量名
container_env_value	变量值
container_no_env_vars	无自定义环境变量
container_add_env_var	添加环境变量
container_no_sftp_channel	暂无可用的 SFTP 通道，请先在「连接与同步」页配置一个 SFTP 连接通道。
container_select_ssh_channel	选择 SSH 通道
container_ssh_channel	SSH 连接通道
container_remote_workspace_path	远程工作区路径
container_announcement_title	常见问题
container_announcement_got_it	知道了
container_download_image	下载镜像
container_download_architecture	架构：%1${'$'}s
container_download_current_source	下载源：%1${'$'}s
container_download_current_source_title	选择下载源
container_download_source_unavailable	当前源暂无此镜像
container_download_start	下载
container_download_redownload	重新下载
container_download_cancel	取消下载
container_download_import	导入
container_download_imported_toast	已导入到容器列表，首次进入终端时自动解压初始化
container_download_failed	下载失败：%1${'$'}s
container_download_list_empty	暂无可用镜像
container_download_done	已下载
container_download_newer_available	有新版可下载
container_download_checksum_failed	文件校验失败，可能被截断或篡改，请重试
container_download_eol_soon	即将停止支持
container_download_eol_ended	已停止支持
container_download_eol_extended	已进入 %1${'$'}s 延长支持
container_download_eol_extended_generic	已进入延长支持
container_download_eol_support_ends	常规支持结束：%1${'$'}s
container_download_eol_fully_ends	完全停止支持：%1${'$'}s
container_download_size	大小：%1${'$'}s
container_download_delete_title	删除下载镜像
container_download_delete_confirm	删除「%1${'$'}s」的下载文件？已导入的容器将无法再解压。
credential_edit	编辑凭据
credential_host	远程主机
credential_toast_saved	凭据已保存
credential_toast_save_failed	保存失败：%1${'$'}s
credential_toast_deleted	凭据已删除
credential_toast_identity_saved	署名与仓库地址已保存
credential_empty_hint	尚未添加凭据，点右上角 + 添加
credential_empty_title	暂无 Git 凭据
credential_empty_desc	点击右上角 + 添加：GitHub 快捷登录或手动配置访问令牌。配置后推送、拉取代码时会自动完成身份认证
credential_add_sheet_title	添加凭据
credential_add_sheet_subtitle	选择快捷授权登录或手动配置访问凭据
credential_add_manual	手动添加
credential_add_manual_desc	填写远程主机、用户名与访问令牌
credential_add_github_title	GitHub 快捷登录
credential_add_github_desc	通过 GitHub 账号一键授权，无需手动创建 Token
credential_add_manual_title	手动配置凭据
credential_add_manual_subtitle	适用于 GitLab、Gitee、自建服务器或个人访问令牌
credential_add_recommend	推荐
credential_quick_login	快速登录
credential_quick_login_desc	通过 GitHub 设备码授权登录
credential_quick_login_title	快速登录
credential_quick_login_github_desc	使用 GitHub 账号授权登录
credential_device_title	GitHub 授权登录
credential_device_subtitle	通过 GitHub 官方设备流安全授权访问权限
credential_device_requesting	正在请求设备码…
credential_device_instruction	在浏览器打开下方地址，输入设备码完成授权
credential_device_card_label	设备授权码
credential_device_tap_to_copy	点击复制
credential_device_code_copied	已复制设备码
credential_device_code_copied_opening	已复制设备码，正在打开浏览器…
credential_device_authorize_and_open	复制并前往授权
credential_device_copy_code	仅复制设备码
credential_device_open_browser	打开浏览器
credential_device_step_1	1. 复制上方 8 位设备验证码
credential_device_step_2	2. 前往 GitHub 验证页面粘贴并确认授权
credential_device_waiting	等待浏览器授权完成…
credential_device_waiting_desc	完成授权后将自动绑定并保存凭据
credential_device_success_title	授权成功
credential_device_success	已成功关联账号 %1${'$'}s 并保存凭据
credential_device_install_required	已登录为 %1${'$'}s。还需把 AiCode 安装到你的仓库，之后才能推送/拉取代码。
credential_device_install_now	去安装
credential_device_install_later	稍后
credential_device_failed_title	授权未能完成
credential_device_failed	登录失败：%1${'$'}s
credential_device_expired	设备码已过期，请重试
credential_device_denied	授权已被拒绝
credential_device_flow_disabled	该应用未启用设备码流，请在 GitHub App 设置中开启
credential_device_bad_client	客户端 ID 无效，请检查应用配置
credential_device_unknown_error	未知错误
credential_prompt_title	需要 %1${'$'}s 的登录凭据
credential_remote_host	远程主机
credential_prompt_desc	填完即保存并自动重试。该凭据同时会保存到容器内 git，终端与 AI 执行裸 git 命令时也可直接使用。
credential_save_and_retry	保存并重试
diff_no_changes	无差异
diff_binary_file	二进制文件，不支持文本差异对比
diff_file_too_large	文件过大，已跳过差异计算
diff_file_too_large_desc	超过 2000 行或含超长行的文件不做行级 diff，以避免渲染与内存压力。
git_credentials_and_identity	凭据与署名
git_title	Git
git_refresh	刷新
git_tab_status	状态
git_tab_branches	分支
git_tab_commits	提交
git_computing_diff	正在计算差异…
git_status_clean	工作区干净，无改动
git_commit_needs_identity	尚未配置提交署名，请在「凭据与署名」页填写用户名和邮箱
git_staged_count	已暂存 (%1${'$'}s)
git_unstage	取消暂存
git_modified_count	已修改 (%1${'$'}s)
git_stage	暂存
git_untracked_count	未跟踪 (%1${'$'}s)
git_clean	工作区干净
git_has_changes	工作区有改动
git_no_branch	(无分支)
git_staged_label	已暂存
git_modified_label	已修改
git_untracked_label	未跟踪
git_commit_changes	提交更改
git_no_identity	未配置署名，无法提交。请在「凭据」页填写 user.name / user.email。
git_stage_all	暂存全部
git_pull	拉取
git_push	推送
git_loading_branches	正在加载分支列表…
git_no_branches	暂无分支
git_no_checked_out_branch	未检出分支
git_switch_branch	切换分支
git_detached_head_warning	切换到标签 %1${'$'}s 将进入 detached HEAD 状态——不处于任何分支上，新提交不属于任何分支。确定继续？
git_create_tracking_branch	从远程分支 %1${'$'}s 创建本地跟踪分支并切换？
git_switch_confirm	确定切换到 %1${'$'}s？
git_new_branch	新建分支
git_branch_name	分支名
git_base_branch	基准分支
git_create_and_switch	创建并切换
git_delete_remote_branch	删除远程分支
git_delete_branch	删除分支
git_delete_remote_branch_confirm	删除远程分支 %1${'$'}s？该操作不可撤销。
git_delete_local_branch_confirm	删除本地分支 %1${'$'}s？
git_rename_branch	重命名分支
git_rename_to	将 %1${'$'}s 重命名为：
git_new_branch_name	新分支名
git_create_tag_desc	从当前 HEAD（%1${'$'}s）创建轻量标签
git_tag_name	标签名
git_delete_tag	删除标签
git_delete_tag_confirm	删除本地标签 %1${'$'}s？
git_checked_out	当前检出
git_current_branch	当前分支
git_remote	远程
git_tags	标签
git_new	新增
git_no_commits	暂无提交记录
git_commit_count	提交记录 (%1${'$'}s)
git_credentials_count	凭据 (%1${'$'}d)
git_load_more_commits	上拉加载更早提交
git_no_more_commits	没有更多了
git_latest_commit	最新提交
git_recent_commit	最近提交
git_expanded	已展开
git_latest_date	最新日期
git_files_changed	%1${'$'}s 个文件改动
git_no_files_changed	该提交无文件改动
git_loading_files	正在加载改动文件…
git_not_a_repo	当前工作区还不是 Git 仓库
git_init_desc_primary	可在此初始化新仓库，开始版本管理
git_init_desc_secondary	若子目录已有 Git 仓库，也可直接选择使用
git_init_repo	初始化新仓库
git_clone_repo	克隆远程仓库
git_clone_dialog_title	克隆 Git 仓库
git_clone_desc	将远程仓库克隆到当前工作区根目录（工作区需为空）。若为私有仓库，可先在右上角凭据管理中配置访问凭据。
git_clone_url_label	仓库地址
git_clone_url_hint	https://github.com/user/repo.git
git_cloning_hint	正在克隆仓库，请稍候…
git_commit_dialog_title	提交更改
git_commit_message	提交信息
git_tracking_branch	跟踪 %1${'$'}s
git_no_upstream	未设置上游
git_detached_head_label	detached HEAD：当前不在任何分支上
git_ahead_count	待推送 %1${'$'}s
git_behind_count	待拉取 %1${'$'}s
git_copy_short_hash	复制短哈希
git_copy_full_hash	复制完整哈希
git_branch_synced	已同步
git_merge_commit	合并提交
git_unstage_all_confirm	将取消所有文件的暂存，但不会删除文件内容。确定继续吗？
git_pull_dirty_confirm	当前工作区有未提交改动，拉取可能导致冲突。建议先提交或暂存。仍要继续拉取吗？
git_pull_continue	继续拉取
git_switch_repo	切换仓库
git_pick_existing_repo	选择已有仓库
git_repo_selector_title	选择仓库
git_repo_not_repo	非仓库
git_pick_repo_empty	当前工作区没有可选的目录
git_push_master_confirm_title	分支命名建议
git_push_master_confirm_desc	当前本地分支为 master，GitHub 与 GitLab 推荐使用 main 作为默认主分支。是否将本地分支重命名为 main 后再推送？
git_push_rename_to_main	重命名为 main 并推送
git_push_keep_master	保留 master 推送
git_toast_load_branches_failed	加载分支失败：%1${'$'}s
git_toast_refresh_failed	刷新失败：%1${'$'}s
git_toast_action_refresh_failed	%1${'$'}s（刷新失败）
git_toast_no_remote_pull	未配置远程仓库，无法拉取
git_toast_no_remote_push	未配置远程仓库，无法推送
git_toast_load_more_failed	加载更多失败：%1${'$'}s
git_toast_action_success	%1${'$'}s成功
git_toast_action_failed	%1${'$'}s失败：%2${'$'}s
git_toast_checkout_success	已切换到 %1${'$'}s
git_toast_checkout_failed	切换失败：%1${'$'}s
git_toast_not_repo	该目录不是 Git 仓库
git_toast_switch_repo_failed	切换仓库失败：%1${'$'}s
git_toast_diff_failed	加载差异失败
git_toast_path_copied	已复制路径
git_action_stage_all	全部暂存
git_action_unstage_all	全部取消暂存
git_action_commit	提交
git_action_init	初始化
git_action_clone	克隆
git_action_create_branch	创建分支
git_action_delete_branch	删除分支
git_action_delete_remote_branch	删除远程分支
git_action_rename_branch	重命名分支
git_action_create_tag	创建标签
git_action_delete_tag	删除标签
git_action_view_diff	查看差异
git_action_revert	回退改动
git_action_restore_file	恢复文件
git_action_revert_all	全部回退
git_action_delete_file	删除文件
git_action_delete_dir	删除目录
git_action_copy_path	复制路径
git_revert_confirm_worktree	把 %1${'$'}s 还原成上次暂存或提交的内容？未暂存的改动会丢失，且无法找回。
git_revert_confirm_head	把 %1${'$'}s 还原到上次提交的内容？该文件已暂存和未暂存的改动都会丢失，且无法找回。
git_restore_file_confirm	恢复已删除的 %1${'$'}s？将从上次提交取回这个文件。
git_revert_all_confirm	丢弃全部未暂存的改动（%1${'$'}d 个文件），还原到上次暂存或提交的内容？已暂存的内容不受影响，但丢弃的改动无法找回。
git_delete_untracked_confirm	删除 %1${'$'}s？它从未被提交过，删除后无法通过 Git 恢复。
git_action_merge	合并分支
git_merge_into	合并到 %1${'$'}s
git_merge_confirm	确定将分支 %1${'$'}s 合并到当前分支（%2${'$'}s）吗？
git_merge_no_ff	创建合并提交（--no-ff）
git_tab_stash	储藏
git_stash_title	储藏箱
git_action_stash	储藏改动
git_stash_message_hint	储藏说明（可选）
git_stash_include_untracked	包含未跟踪文件（-u）
git_stash_empty	暂无储藏记录
git_stash_pop	弹出 (Pop)
git_stash_apply	应用 (Apply)
git_stash_drop	丢弃 (Drop)
git_stash_drop_confirm	确定丢弃储藏 %1${'$'}s 吗？该操作不可撤销。
git_stash_clear	清空全部
git_stash_clear_confirm	确定清空所有储藏记录吗？该操作不可撤销。
git_stash_count	储藏 (%1${'$'}d)
git_conflicted_count	存在冲突 (%1${'$'}s)
git_merge_pending_title	合并待提交
git_merge_pending_hint	当前没有未解决的冲突。请确认并暂存需要保留的改动，然后提交以完成合并。
git_action_abort_merge	中止合并
git_abort_merge_confirm	确定中止当前合并吗？未解决的冲突改动将被放弃，工作区将还原到合并前的状态。
git_commit_ai_generate	AI 生成
git_commit_generating	正在生成…
git_commit_amend	追加到上次提交 (--amend)
git_toast_no_diff_for_commit	暂存区与工作区均无差异，无法生成提交信息
git_toast_generate_commit_failed	AI 生成提交信息失败，请检查模型或网络
git_open_in_editor	在编辑器中打开
git_create_branch_from_commit	基于此提交创建分支
git_create_tag_at_commit	在此提交创建标签
git_reset_to_commit	重置到此提交 (Reset)
git_reset_soft	Soft（保留工作区与暂存区）
git_reset_mixed	Mixed（保留工作区，清空暂存区）
git_reset_confirm	确定将当前分支重置到提交 %1${'$'}s 吗？
git_search_commits_hint	搜索提交说明或作者…
git_add_to_gitignore	添加到 .gitignore
git_ignore_file	忽略此文件 (%1${'$'}s)
git_ignore_dir	忽略整个目录 (%1${'$'}s/)
git_ignore_extension	忽略同类型文件 (*.%1${'$'}s)
git_toast_added_to_gitignore	已添加到 .gitignore
git_tab_identity	署名
git_tab_credentials	凭据
git_identity_title	提交署名
git_identity_desc	用于每次提交的 author。容器全局生效，所有工作区共用。
git_repo_url_title	仓库地址
git_remote_url	remote.origin.url
mcp_add	添加 MCP
mcp_edit	编辑 MCP
mcp_view_logs	查看此 MCP 日志
mcp_refresh_tools	刷新工具
mcp_scope_label	作用域
mcp_scope_global	全局
mcp_scope_project	项目
mcp_basic_settings	基础设置
mcp_enabled	是否启用
mcp_name_hint	例如：fetch、github、gitlab
mcp_name_invalid	名称只能包含字母、数字、下划线和连字符
mcp_transport_type	传输类型
mcp_remote_http	远程 HTTP
mcp_local_stdio	本地 stdio
mcp_save_first_hint	请先保存并连接服务器后查看工具列表
mcp_no_tools_hint	未检测到工具，点击右上角刷新按钮重试
mcp_no_description	无描述
mcp_server_url	服务器地址
mcp_server_url_hint	例如：https://api.example.com/sse
mcp_custom_headers	自定义请求头
mcp_no_custom_headers	暂无配置的自定义 Header
mcp_header_name	请求头名称
mcp_header_name_hint	如 Authorization
mcp_header_value	请求头值
mcp_header_value_hint	如 Bearer xxxxxx
mcp_add_header	添加请求头
mcp_empty	还没有配置 MCP 服务器
mcp_empty_hint	点击右上角 + 可以添加远程 HTTP 或本地 stdio 服务器
mcp_disabled	已禁用
mcp_not_connected	未连接
mcp_connected	已连接
mcp_connecting	连接中…
mcp_connection_failed	连接失败
mcp_needs_auth	需要登录
mcp_auth_enable	OAuth 认证
mcp_auth_desc	用于需要浏览器登录授权的远程 MCP 服务
mcp_auth_client_id	client_id（可选）
mcp_auth_client_id_hint	预注册的客户端 ID；留空则尝试动态注册
mcp_auth_server	授权服务器（可选）
mcp_auth_server_hint	覆盖自动发现的授权服务器地址
mcp_auth_advanced	高级认证选项
mcp_auth_login	登录 / 重新授权
mcp_auth_reauthorize	重新授权
mcp_auth_logout	退出登录
mcp_auth_cancel	取消授权
mcp_auth_in_progress	等待浏览器授权…
mcp_auth_in_progress_hint	已在系统浏览器打开登录页，授权完成后将自动返回并连接
mcp_auth_save_first	保存服务器配置后，即可在此发起登录授权
mcp_auth_failed	授权失败：%1${'$'}s
mcp_auth_status_label	授权状态
mcp_auth_status_authorized	已登录
mcp_auth_status_unauthorized	未登录
mcp_auth_status_authorizing	授权中
mcp_auth_success	授权成功，已连接
mcp_search_placeholder	搜索 MCP 服务器…
mcp_search_empty	未找到匹配的 MCP 服务器
mcp_tools_search	搜索工具名称或描述…
mcp_tools_search_empty	未找到匹配的工具
mcp_tools_summary	已启用 %1${'$'}d / %2${'$'}d 个工具
mcp_tools_enable_all	全部启用
mcp_tools_disable_all	全部禁用
mcp_add_sheet_title	添加 MCP 服务器
mcp_add_manual	手动填写
mcp_add_manual_desc	逐个填写地址、请求头或启动命令
mcp_add_from_json	从 JSON 导入
mcp_add_from_json_desc	粘贴标准 mcpServers 配置批量导入
mcp_json_import_title	从 JSON 导入
mcp_json_import_hint	粘贴 mcpServers 配置，例如 {"mcpServers":{"demo":{"url":"https://..."}}}
mcp_json_import_action	导入
mcp_json_import_empty	未解析到任何 MCP 服务器
mcp_type_stdio	stdio
mcp_tools_count	%1${'$'}d 个工具
mcp_details	详情
prompts_title	提示词
prompts_sort_long_press	长按可调整顺序
prompts_empty	暂无可用片段
prompts_source_project	项目
prompts_source_global	全局
prompts_source_local	本地
prompts_source_builtin	内置
prompts_field_basic	基本信息
prompts_field_number	编号
prompts_field_number_placeholder	两位数字，如 35
prompts_field_title	名称
prompts_field_title_placeholder	如：我的安全规则
prompts_field_summary	摘要（可选）
prompts_field_summary_placeholder	简述片段内容，不会注入提示词
prompts_field_content	片段内容
prompts_field_content_placeholder	写模型要遵守的内容
prompts_editor_new	新建提示词
prompts_editor_edit	编辑提示词
prompts_add_prompt	添加片段
prompts_reset_custom	重置自定义提示词
prompts_reset_confirm	将删除当前项目与全局层里的所有自定义提示词，各编号回落到内置提示词。此操作不可撤销。
prompts_help	帮助
prompts_help_body	提示词按编号排序注入：内置 → 全局 → 项目（就近覆盖）。点击行预览；长按拖拽调整顺序；左滑删除自定义片段；勾选进入多选。
window_minimize	最小化
window_maximize	最大化 / 还原
window_close	关闭窗口
prompts_disable_builtin	完全禁用内置提示词
prompts_disable_builtin_hint	只保留你的片段；需要自己补全全部规则，谨慎开启
skills_empty	还没有安装技能
skills_empty_hint	点右上角「+」新建或导入
skills_no_project_skills	当前项目暂无技能
skills_no_global_skills	暂无全局技能
skills_enable	是否启用
skills_summary	摘要
skills_delete_confirm_title	删除技能
skills_delete_confirm_message	将删除「%1${'$'}s」的技能目录，此操作不可恢复。确定删除？
skills_description	描述
skills_instructions	指令正文
skills_add	添加技能
skills_edit	编辑技能
skills_editor_new	新建技能
skills_editor_edit	编辑技能
skills_editor_basic	基本信息
skills_editor_scope	作用域
skills_scope_global	全局
skills_scope_project	当前项目
skills_name_hint	建议用英文小写，如 pdf-report
skills_description_hint	简述何时该用它，AI 据此判断要不要加载
skills_instructions_hint	写清这类任务的标准流程与注意事项，支持 Markdown
skills_editor_error_invalid_name	名称不合法：不能为空，也不能包含路径分隔符等特殊字符
skills_editor_error_empty_instructions	指令正文不能为空
skills_editor_error_name_conflict	该作用域下已存在同名技能
skills_editor_error_io	写入技能文件失败，请稍后重试
skills_add_sheet_title	添加技能
skills_add_manual	手动新建
skills_add_manual_desc	填写名称与正文，从头新建一个技能
skills_add_from_file	从文件导入
skills_add_from_file_desc	选择含指令正文的 .md 文件
skills_add_from_zip	从压缩包导入
skills_add_from_zip_desc	选择 .zip，可含多个技能
skills_import_running	正在导入…
skills_import_result_title	导入结果
skills_import_success	已导入 %1${'$'}d 个技能
skills_import_partial	已导入 %1${'$'}d 个，%2${'$'}d 个跳过
skills_import_failure_line	%1${'$'}s：%2${'$'}s
skills_import_error_invalid_name	名称不合法
skills_import_error_name_conflict	已存在同名技能
skills_import_error_empty_content	内容为空
skills_import_error_no_skill	压缩包里没有找到技能
skills_import_error_invalid_archive	压缩包无法读取，请确认是有效的 .zip
skills_import_error_unsupported_file	文件类型不支持
skills_import_error_io	写入技能文件失败
settings_subagents	子代理
subagents_empty	还没有自定义子代理
subagents_empty_hint	点右上角「+」新建一个，或把 agent 的 .md 定义放进 ~/.aicode/agents/
subagents_no_project	当前项目暂无子代理
subagents_no_global	暂无全局子代理
subagents_delete_confirm_title	删除子代理
subagents_delete_confirm_message	将删除「%1${'$'}s」的定义文件，此操作不可恢复。确定删除？
subagent_model	模型
subagent_tools	工具集
subagent_inject	注入的提示词
subagent_prompt	agent 提示词
subagent_source_file	定义文件
subagent_inherit_parent	继承父会话
subagent_all_tools	继承全量工具（不含 task）
subagent_tools_allow	仅允许：%1${'$'}s
subagent_tools_deny	禁用：%1${'$'}s
subagent_inject_none	不注入任何额外提示词
subagent_add	新建子代理
subagent_edit	编辑子代理
subagent_enable	启用该子代理
subagent_enable_hint	关闭后 AI 不再能派发它，已在运行的子会话不受影响
subagent_editor_new	新建子代理
subagent_editor_edit	编辑子代理
subagent_editor_basic	基本信息
subagent_editor_description	用途描述
subagent_editor_scope	作用域
subagent_scope_global	全局
subagent_scope_project	当前项目
subagent_name_hint	建议用英文小写，如 researcher
subagent_description_hint	简述何时该派它，主会话 AI 据此选择
subagent_editor_model_group	模型
subagent_editor_effort	思考强度
subagent_editor_mode	默认模式
subagent_editor_tools_allow	工具白名单
subagent_editor_tools_allow_hint	不选则开放全部工具（不含 task）
subagent_editor_tools_deny	工具黑名单
subagent_editor_tools_deny_hint	优先于白名单生效
subagent_editor_tools_search	搜索工具
subagent_editor_selected_count	已选 %1${'$'}d 个
subagent_editor_none	无
subagent_editor_clear	清空
subagent_prompt_hint	写清它的角色、工作方式与产出要求
subagent_no_models	暂无可用模型。请先在「AI 供应商」中添加并配置模型。
subagent_editor_error_invalid_name	名称不合法：不能为空，也不能包含路径分隔符等特殊字符
subagent_editor_error_empty_prompt	提示词正文不能为空
subagent_editor_error_name_conflict	该作用域下已存在同名子代理
subagent_editor_error_io	写入定义文件失败，请稍后重试
subagent_inject_base	子代理基线
subagent_inject_base_hint	工具用法与路径约定，轻量、节省 Token
subagent_inject_main_rules	完整通用规则
subagent_inject_main_rules_hint	与「子代理基线」二选一即可；两个都开会重复注入规则、多占上下文
subagent_inject_skills	技能清单
subagent_inject_skills_hint	允许它加载并执行项目技能
subagent_inject_memory	记忆摘要
subagent_inject_memory_hint	注入全局与项目长期记忆
subagent_inject_project_rules	项目规则
subagent_inject_project_rules_hint	注入 AGENTS.md 或 CLAUDE.md
mcp_command	启动命令
mcp_command_hint	例如：npx
mcp_args	命令参数
mcp_no_args	无额外命令行参数
mcp_arg_value	参数值
mcp_arg_hint	如 -y 或 @modelcontextprotocol/server-xxx
mcp_add_arg	添加参数
mcp_env_vars	环境变量
mcp_no_env_vars	无自定义环境变量
mcp_env_name	变量名称
mcp_env_name_hint	如 API_KEY
mcp_env_value	变量值
mcp_env_value_hint	如 sk-xxxx
mcp_add_env_var	添加环境变量
perm_rules_short	对话中选「始终允许」会按命令或路径生成记忆规则
perm_current_project	当前项目：%1${'$'}s
perm_current_project_none	当前项目（未选择）
perm_no_project_rules	本项目暂无规则
perm_global	全局
perm_no_global_rules	暂无全局规则
perm_entire_tool	整个工具
perm_deny	禁止
perm_promote_to_global	提升为全局
perm_safety_group	安全防护
perm_disable_safety_interception	禁用安全拦截
perm_disable_safety_interception_desc	开启后 AUTO 模式下不再拦截灾难性删除，Shizuku 也不再逐次确认
perm_safety_confirm_title	确认禁用安全拦截？
perm_safety_confirm_message	禁用后，AUTO 模式下连灾难性删除也不再拦截，Shizuku 工具也不再逐次确认。请谨慎操作。
perm_safety_confirm_action	确认（%1${'$'}d）
perm_safety_confirm_action_ready	确认
provider_section_basic	基本信息
provider_section_type	上游格式
provider_section_options	选项
provider_show_api_key	显示密钥
provider_hide_api_key	隐藏密钥
provider_api_key	API Key
provider_base_url	Base URL
provider_api_key_required	请先填写 API Key
provider_fetch_missing_fields	响应缺少列表字段
provider_fetch_missing_fields_detail	响应缺少列表字段 (models 或 data): %1${'$'}s
provider_fetch_success	成功拉取到 %1${'$'}d 个模型 · %2${'$'}dms
provider_fetch_http_error	HTTP %1${'$'}d · %2${'$'}dms
provider_test_success	连接正常 · %1${'$'}dms
common_error	错误
common_select	选择
provider_model_context_total	总上下文窗口 (Token)
provider_model_window_invalid	请输入有效的正整数，或留空使用自动识别值
provider_model_context_input	输入窗口 (Token)
provider_model_context_output	输出窗口 (Token)
provider_new	新供应商
provider_add	添加供应商
provider_edit	编辑供应商
provider_add_model	添加模型
provider_config	配置
provider_full_url	完整 URL
provider_full_url_desc	Base URL 即完整请求地址，不自动拼接默认路径
provider_headers_title	自定义请求头
provider_headers_desc	完全覆盖该供应商请求的同名默认头
provider_headers_name	Header 名
provider_headers_name_hint	例如 Authorization
provider_headers_value	值
provider_headers_value_hint	如 Bearer sk-xxx
provider_headers_add	添加请求头
provider_headers_remove	删除请求头
provider_headers_manage	管理
provider_headers_empty	暂无请求头，点击下方添加
provider_headers_count	已配置 %1${'$'}d 个请求头
provider_response_api	Response API (新版)
provider_interactions_api	Interactions API (新版)
provider_models_count	模型（%1${'$'}s）
provider_models_count_tag	%1${'$'}d 个模型
provider_fetch_models	拉取模型
provider_add_all_models	全部添加
provider_add_all_models_title	添加全部模型
provider_add_all_models_confirm	确定将拉取到的 %1${'$'}d 个模型全部添加至当前提供商？
provider_models_select	多选
provider_models_select_all	全选
provider_models_deselect_all	取消全选
provider_models_selected_count	已选 %1${'$'}d 项
provider_models_batch_test	测试
provider_models_batch_delete	删除
provider_models_batch_delete_title	删除模型
provider_models_batch_delete_confirm	确定删除选中的 %1${'$'}d 个模型吗？
common_select_mode	多选
common_select_all	全选
common_deselect_all	取消全选
common_selected_count	已选 %1${'$'}d 项
common_batch_delete	删除
common_batch_enable	启用
common_batch_disable	停用
common_batch_reconnect	重连
common_batch_deleting	正在删除…
providers_batch_delete_title	删除提供商
providers_batch_delete_confirm	确定删除选中的 %1${'$'}d 个提供商吗？此操作不可恢复。
mcp_batch_delete_title	删除 MCP 服务器
mcp_batch_delete_confirm	确定删除选中的 %1${'$'}d 个 MCP 服务器吗？
skills_batch_delete_title	删除技能
skills_batch_delete_confirm	将删除选中的 %1${'$'}d 个技能目录，此操作不可恢复。确定删除？
subagents_batch_delete_title	删除子代理
subagents_batch_delete_confirm	将删除选中的 %1${'$'}d 个子代理定义文件，此操作不可恢复。确定删除？
prompts_batch_delete_title	删除提示词
prompts_batch_delete_confirm	确定删除选中的 %1${'$'}d 条自定义提示词吗？对应编号将回落到下一层来源。
container_download_batch_delete_title	删除已下载镜像
container_download_batch_delete_confirm	删除选中的 %1${'$'}d 个镜像文件？已导入的容器将无法再解压。
provider_model_name	模型名称
provider_model_already_added	该模型已添加
provider_edit_model	编辑模型
provider_model_section_price	价格
provider_model_price_input	输入价格 (USD/1M tokens)
provider_model_price_output	输出价格 (USD/1M tokens)
provider_model_price_cache_read	缓存读取价格 (USD/1M tokens)
provider_model_price_cache_write	缓存写入价格 (USD/1M tokens)
provider_model_cap_vision	图片输入
provider_model_cap_image_output	图片输出
provider_model_capabilities	能力
provider_model_capability_tools	工具调用
provider_model_capability_reasoning	推理能力
provider_filter_models_hint	输入模型名称筛选
provider_fetch_failed	拉取失败：%1${'$'}s
provider_no_matching_models	没有匹配的模型
provider_models_aligned_removed	已移除 %1${'$'}d 个远端已不存在的模型
provider_please_wait	请稍候…
provider_test	测试
provider_brand_doubao	豆包
provider_brand_zhipu	智谱
provider_brand_tongyi_qianwen	通义千问
provider_brand_hunyuan	混元
provider_brand_siliconflow	硅基流动
provider_model_icon	模型图标
providers_empty	还没有供应商，点右上角 + 添加
provider_sort_long_press	长按拖动排序
log_level	日志等级
log_level_desc	记录低于该等级的日志将被忽略。开发期建议 VERBOSE（全部记录）；NONE 关闭日志。
log_file_location	日志文件位于 Android/data//files/logs/。
provider_no_models	暂无模型
provider_no_models_hint	点击右上方按钮添加模型
remote_edit_connection	编辑连接通道
remote_add_connection	添加连接通道
remote_connection_name_hint	连接名称
remote_connection_sort_long_press	长按拖动排序
remote_host_address	主机地址
remote_port	端口
remote_password	密码
remote_auth_password	密码
remote_auth_key	密钥
remote_login_key	登录密钥
remote_select_key	选择密钥
remote_no_keys_hint	暂无密钥，请先到「SSH 主机密钥」页添加
remote_key_passphrase	口令（可选）
remote_toggle_password	切换密码可见性
remote_test_connection	测试连通性
remote_edit_workspace	编辑挂载目录
remote_add_workspace	添加挂载目录
remote_select_channel	选择远程通道
remote_link_channel	关联远程连接通道
remote_target_dir	远程目标目录
remote_browse_dir	浏览目录
remote_select_local_workspace	选择本地工作区
remote_mount_already_exists	该挂载已存在：相同的通道、远程目录与工作区组合
remote_map_to_local	映射到本地工作区
remote_no_local_workspace	暂无本地工作区，请先在侧边栏创建一个。
remote_auto_connect_on_start	应用启动时自动连接
remote_auto_connect_and_sync	应用启动时自动连接并同步该工作区
remote_select_remote_dir	选择远程目录
remote_current_path	当前路径：%1${'$'}s
remote_load_failed	加载失败：%1${'$'}s
remote_parent_dir	.. (上一级)
remote_confirm_select	确认选择
ftp_usage_title	使用说明
ftp_usage_item_1	手机与电脑连接同一 WiFi
ftp_usage_item_2	电脑文件管理器输入下方地址访问
ftp_usage_item_3	即可管理手机工作区里的项目
ftp_running	运行中：%1${'$'}s
ftp_not_running	服务未开启
ftp_error	错误：%1${'$'}s
ftp_config_title	服务参数配置
ftp_listen_port	监听端口
ftp_login_username	登录用户名
ftp_login_password	登录密码
ftp_allow_anonymous	允许匿名访问
ftp_anonymous_desc	无需输入用户名和密码即可连接
ftp_anonymous_confirm_title	允许匿名访问？
ftp_anonymous_confirm_message	开启后，同一局域网内任何设备无需凭据即可读写你的工作区，存在被他人访问的风险。
ftp_password_required	请先设置登录用户名与密码
ftp_reserved_username	anonymous 是匿名登录的保留名称，请使用其它用户名
ftp_user_cache_cleanup_failed	无法清理旧 FTP 登录信息，服务未启动，请重试
ftp_auto_start	应用启动时自启
ftp_auto_start_desc	应用启动时后台自动运行 FTP 服务
ftp_config_saved	配置已保存
ftp_save_config	保存配置
ftp_copy_address	复制地址
ftp_address_copied	地址已复制
remote_workspace_title	连接与同步
remote_tab_connections	连接配置
remote_tab_mounts	挂载目录
remote_tab_ftp	内置FTP
remote_tab_sync	同步设置
remote_no_connections	暂无连接配置
remote_no_connections_desc	添加 SFTP / FTP 连接
remote_no_workspaces	暂无挂载目录
remote_no_workspaces_desc	本地改动自动同步到远程，远程文件可手动下载
remote_hint_title	提示
remote_add_channel_first	请先在「连接配置」中添加一个远程通道。
remote_go_add	去添加
remote_upload_failed	上传失败：%1${'$'}s
remote_upload_success	上传完成
remote_upload_full_failed	全量上传失败：%1${'$'}s
remote_upload_full_success	全量上传完成
remote_clear_index_done	已清除同步记录
remote_download_all_failed	全量下载失败：%1${'$'}s
remote_download_all_success	全量下载完成
remote_connect_success	连接成功
remote_connect_failed	连接失败：%1${'$'}s
remote_unknown_error	未知错误
ssh_host_key_settings_title	SSH 主机密钥
ssh_host_key_tab_keys	密钥
ssh_host_key_tab_fingerprints	指纹
ssh_login_key_add	添加密钥
ssh_login_key_empty	暂无已保存的登录密钥
ssh_login_key_fingerprint_label	公钥指纹
ssh_login_key_encrypted	加密密钥（需口令）
ssh_login_key_add_method_title	添加登录密钥
ssh_login_key_add_from_file	从文件导入
ssh_login_key_add_from_file_desc	选择设备上的私钥文件
ssh_login_key_add_from_paste	粘贴密钥
ssh_login_key_add_from_paste_desc	直接粘贴私钥内容
ssh_login_key_paste_title	粘贴密钥
ssh_login_key_paste_name_placeholder	留空则自动命名
ssh_login_key_paste_content_placeholder	-----BEGIN OPENSSH PRIVATE KEY-----
ssh_login_key_paste_invalid	私钥内容无效，请检查后重试
ssh_login_key_edit_title	编辑密钥
ssh_login_key_name_label	名称
ssh_login_key_content_label	私钥内容
ssh_login_key_passphrase_placeholder	私钥未加密则留空
ssh_login_key_toggle_content	显示或隐藏私钥内容
ssh_host_key_confirm_title	确认 SSH 主机密钥
ssh_host_key_changed_title	SSH 主机密钥已变化
ssh_host_key_trust	信任并连接
ssh_host_key_reject	拒绝
ssh_host_key_settings_desc	已确认的主机指纹会用于阻止中间人攻击。服务器重装或密钥变化后，请先核实再重新确认。
ssh_host_key_empty	暂无已保存的主机指纹
ssh_host_key_fingerprint_value	指纹：%1${'$'}s
ssh_host_key_remove	删除指纹
ssh_host_key_detail	已保存的服务器主机指纹：\n%1${'$'}s
sync_save_settings	保存设置
sync_settings_title	同步设置
sync_saved	同步设置已保存
sync_follow_gitignore	遵循 .gitignore 规则
sync_gitignore_desc	自动读取项目根目录的 .gitignore 并忽略其中内容。
sync_max_batch_size	单次同步队列上限
sync_batch_size_desc	超出此数量的操作将分批排队执行，防止突发大量修改导致断连。
sync_max_batch_count	最大数量
sync_path_mapping	%1${'$'}s → %2${'$'}s
status_reconnecting	重连中
status_connected	已连接
status_disconnected	未连接
status_connection_failed	连接失败
sync_disconnect	断开连接
sync_upload_changes	上传变更
sync_upload_full	强制全量上传
sync_clear_index	清除同步记录
sync_download_all	下载全部
sync_connect_and_sync	连接并同步
terminal_title	终端
terminal_prepare_error_unknown	未知错误
terminal_start_failed	无法启动终端：\n%1${'$'}s
terminal_retry	重试
terminal_no_open_tabs	没有打开的终端标签
terminal_close_tab	关闭标签
terminal_cleaning_old_container	正在清理旧容器数据…\n已清理 %1${'$'}s 项
terminal_extracting_env	正在解压环境…\n已处理 %1${'$'}s 个文件
terminal_installing_packages	正在安装 python3 / git / pip / node / npm…\n%1${'$'}s
terminal_preparing_env_failed	正在准备容器环境…\n%1${'$'}s
terminal_preparing_env_first_run	正在准备容器环境…\n首次运行会解压容器，请稍候
terminal_settings_title	终端设置
terminal_theme_title	配色主题
terminal_font_size_title	字体大小
terminal_cursor_style_title	光标样式
terminal_cursor_block	方块
terminal_cursor_underline	下划线
terminal_cursor_bar	竖线
terminal_font_title	终端字体
terminal_font_import	导入
terminal_font_default	系统等宽
terminal_font_builtin	JetBrains Mono
terminal_font_delete	删除该字体
terminal_font_import_failed	导入失败，请选择有效的 ttf / otf / ttc 字体文件
terminal_preview_text	终端预览
terminal_theme_follow_app	跟随应用
terminal_theme_github_light	GitHub Light
terminal_theme_termius_dark	Termius Dark
terminal_theme_dracula	Dracula
terminal_theme_one_dark	One Dark
terminal_theme_monokai	Monokai
terminal_theme_github_dark	GitHub Dark
todo_completed	已完成
todo_in_progress	进行中
todo_dashboard_title	任务待办
todo_dashboard_progress	%1${'$'}d/%2${'$'}d 完成
todo_dashboard_all_done	全部完成
tool_instruction	指令
tool_result	结果
tool_expand_remaining	展开剩余 %1${'$'}s 行
tool_changed_files_summary	+%1${'$'}d −%2${'$'}d · %3${'$'}d 个文件
vision_no_models	暂无可用模型。请在「AI 供应商」中添加并配置模型后再选识图模型。
compaction_no_models	暂无可用模型。请在「AI 供应商」中添加并配置模型后再选压缩专用模型。
title_no_models	暂无可用模型。请在「AI 供应商」中添加并配置模型后再选标题总结专用模型。
image_gen_no_models	暂无可用模型。请在「AI 供应商」中添加并配置模型后（需支持图片输出，如 gpt-image-1 / dall-e-3），再选生图模型。
web_search_title	网络搜索
web_search_results_count	找到 %1${'$'}s 条结果
web_search_warning	警告：%1${'$'}s
workspace_select	选择工作区
workspace_switch	切换工作区
workspace_switch_confirm	切换工作区会停止当前工作区所有正在运行的 AI 会话并关闭终端标签。确定要切换吗？
workspace_confirm	确定
workspace_open	打开工作区
workspace_new_internal	新建内部工作区
workspace_empty_hint	暂无工作区，点击「%1${'$'}s」创建一个
workspace_delete	删除工作区
workspace_delete_confirm	确定删除「%1${'$'}s」？该目录下的所有文件及该工作区的聊天记录都将被删除，且无法恢复。
workspace_new_workspace	新建工作区
workspace_name_hint	例如 my-project
workspace_name_exists	已存在同名工作区
workspace_remote_root_create_failed	远程工作区目录创建失败，请检查路径与服务器权限
workspace_remote_default_create_failed	远程默认工作区创建失败，请检查服务器权限与磁盘空间
workspace_add_external	添加外部工作区
workspace_external_added	已添加外部工作区「%1${'$'}s」
workspace_external_warning_title	添加外部工作区
workspace_external_warning_message	AI 会直接读写所选目录中的真实文件。公共存储不支持软链，npm、yarn、pnpm、git 涉及软链的操作可能失败；移除工作区只解除关联，不会删除目录内容。
workspace_external_warning_dont_show_again	不再提示
workspace_external_warning_continue	继续
workspace_external_remove_confirm	移除工作区「%1${'$'}s」？仅解除关联，不会删除该目录及其文件。
workspace_external_remove_confirm_sessions	移除工作区「%1${'$'}s」？仅解除关联，不会删除该目录及其文件；该工作区的聊天记录将一并删除。
workspace_external_unresolvable	无法解析所选目录，请从「内部存储」或「SD 卡」根目录进入选择，暂不支持其它文件提供方
workspace_external_invalid	所选不是有效目录
workspace_external_unsupported_path	该目录路径包含不支持的字符「:」
workspace_external_unwritable	该目录不可写，请在系统设置中授予存储权限后重试
workspace_external_exists	该目录已作为工作区
workspace_external_unavailable_hint	该目录当前不可用（可能已被移动或删除），可移除该记录
workspace_unavailable_current	工作区「%1${'$'}s」当前不可用（目录可能已被移动或删除）
workspace_unavailable_fallback	工作区「%1${'$'}s」当前不可用，已切换到「%2${'$'}s」
container_channel_deleted	通道已删除
chat_more_options	更多选项
chat_action_edit	编辑消息
chat_action_copy	复制文本
chat_action_fork	从此处新建分支
chat_action_delete	删除消息
chat_fork_title	Fork of %1${'$'}s
chat_edit_dialog_title	编辑消息
chat_edit_dialog_hint	请输入消息内容
crash_title	应用遇到未知错误
crash_message	应用出现异常，错误信息已自动记录。请复制下方内容并反馈给开发者，帮助我们改进应用。
crash_copy	复制错误信息
crash_copied	错误信息已复制
crash_restart	重启应用
crash_exit	退出应用
crash_log_hint	完整日志文件位置：%1${'$'}s
crash_report_title	AiCode 崩溃报告
crash_report_time	时间：%1${'$'}s
crash_report_version	版本：%1${'$'}s
crash_report_device	设备：%1${'$'}s
crash_report_thread	线程：%1${'$'}s
crash_report_log	日志文件：%1${'$'}s
crash_report_screen	页面：%1${'$'}s
crash_report_mode	工作区模式：%1${'$'}s
crash_report_memory	内存：%1${'$'}s
crash_mode_local	本地 PRoot 容器
crash_mode_remote	远程 SSH
crash_mode_unknown	未知
crash_screen_chat	聊天
crash_screen_settings	设置
crash_screen_terminal	终端
crash_screen_git	Git
crash_screen_unknown	未知
crash_banner_title	应用运行异常
crash_section_environment	环境与运行状态
crash_section_stack	异常调用栈
crash_field_workspace_mode	工作区模式
crash_field_screen	发生页面
crash_field_version	应用版本
crash_field_device	设备型号
crash_field_thread	异常线程
crash_field_memory	内存用量
crash_field_time	发生时间
dashboard_fetching	正在加载面板…
dashboard_fetch_failed_retry	加载失败，点击重试
dashboard_tap_to_query	点击刷新面板
common_open_link_failed	无法打开链接
common_copied_with_title	已复制：%1${'$'}s
dashboard_exec_failed	执行失败
dashboard_query_failed	查询失败
token_stats_hour_suffix	时
git_create_tag_title	新建标签
remote_delete_connection_confirm	确定删除连接「%1${'$'}s」吗？使用该连接的同步配置将不可用。
remote_delete_mount_confirm	确定删除工作区映射「%1${'$'}s」吗？本地与远程文件不会被删除。
proxy_title	网络代理
proxy_enable	启用代理
proxy_enable_subtitle	开启后应用网络请求与容器内命令统一走代理
proxy_type	代理类型
proxy_type_http	HTTP
proxy_type_socks5	SOCKS5
proxy_test_title	连通性测试
proxy_test	测试代理
proxy_testing	测试中…
proxy_test_ok	代理可用（HTTP %1${'$'}d）
proxy_test_http_error	代理返回异常状态 HTTP %1${'$'}d：%2${'$'}s
proxy_test_connect_failed	连接代理失败：%1${'$'}s
proxy_test_invalid_config	请先填写有效的服务器地址与端口（1-65535）
provider_proxy_off	未启用
provider_multi_key_title	多 Key 模式
provider_multi_key_subtitle	同一供应商下配多个 Key，密钥不可用时自动切换并重发
provider_multi_key_manage	多 Key 管理
provider_multi_key_summary	%1${'$'}d 个 · %2${'$'}s
provider_multi_key_list	Key 列表
provider_multi_key_item	密钥 #%1${'$'}d
provider_multi_key_empty	点此添加第一个 Key
provider_multi_key_add	添加 Key
provider_multi_key_weight	调度权重
provider_multi_key_weight_hint	默认 1 · ≤ 0 时不参与 · 最大 1,000,000
provider_multi_key_strategy	取用策略
provider_multi_key_strategy_sequential	顺序
provider_multi_key_strategy_sequential_desc	始终用靠前的 Key，失败达阈值才切下一个
provider_multi_key_strategy_round_robin	轮询
provider_multi_key_strategy_round_robin_desc	新会话轮流起步以摊平额度；同一会话内不换 Key，保持缓存命中
provider_multi_key_failover	失败切换
provider_multi_key_status_codes	自动切换状态码
provider_multi_key_status_codes_hint	逗号分隔，留空使用默认 401,402,403,429
provider_multi_key_cooldown	冷却时长
provider_multi_key_cooldown_desc	被切走的 Key 隔多久重新纳入候选
provider_multi_key_cooldown_value	%1${'$'}d 分钟
provider_multi_key_cooldown_off	不冷却
proxy_test_url_label	测试地址
proxy_test_url_placeholder	https://www.google.com
proxy_server	代理服务器
proxy_host	服务器地址
proxy_host_placeholder	如 127.0.0.1
proxy_port	端口
proxy_port_placeholder	如 7890
proxy_auth	认证（可选）
proxy_username	账号（可选）
proxy_password	密码（可选）
proxy_no_proxy	不走代理的地址
proxy_no_proxy_hint	逗号分隔；支持通配符 *、host:port、CIDR 网段
provider_proxy_section	代理（可选）
provider_proxy_enable	为该供应商单独配置代理
provider_proxy_enable_desc	开启后该供应商的请求走专属代理；关闭则跟随全局代理
settings_rerun_onboarding	重新运行新手引导
onboarding_skip	跳过
onboarding_next	下一步
onboarding_done	完成
onboarding_step_badge	新手引导 %1${'$'}d/%2${'$'}d
onboarding_open_sidebar_title	打开侧边栏
onboarding_open_sidebar_desc	点击左上角按钮打开侧边栏，前往设置配置你的 AI 模型。
onboarding_enter_settings_title	进入设置
onboarding_enter_settings_desc	点击进入 App 设置，管理你的 AI 供应商与模型配置。
onboarding_config_provider_title	进入 AI 供应商
onboarding_config_provider_desc	点击进入 AI 供应商管理列表，准备配置模型供应商。
onboarding_provider_add_title	添加供应商
onboarding_provider_add_desc	点击「+」添加供应商，支持 OpenAI、Claude、Gemini、DeepSeek 等官方供应商或自定义中转。
onboarding_provider_config_info_title	填写配置信息
onboarding_provider_config_info_desc	输入你的 API 密钥。官方预设已内置根地址，第三方中转可修改 Base URL。
onboarding_provider_fetch_models_title	拉取模型
onboarding_provider_fetch_models_desc	配置好密钥后，点击「拉取模型」即可自动同步可用模型列表。
onboarding_provider_select_fetched_model_title	添加拉取的模型
onboarding_provider_select_fetched_model_desc	已获取到可用模型，点击添加按钮将该模型加入当前供应商。
onboarding_open_model_picker_title	打开模型面板
onboarding_open_model_picker_desc	回到主页，点击输入框左侧的模型图标打开选择面板。
onboarding_choose_model_in_sheet_title	选择会话模型
onboarding_choose_model_in_sheet_desc	点击刚刚添加的模型，指定当前对话使用的 AI。
onboarding_send_message_title	开始对话
onboarding_send_message_desc	模型已就绪，在输入框输入你的编程需求或问题，开始与 AI 助手协同工作。
browser_title	浏览器
browser_address_hint	输入网址或搜索
browser_back	后退
browser_forward	前进
browser_reload	刷新
browser_not_ready	浏览器未就绪，请在地址栏输入网址开始浏览
browser_new_tab	新建标签页
browser_close_tab	关闭标签页
browser_tab_default_title	新标签页
browser_tabs_count	标签页 (%1${'$'}d)
browser_empty_hint	在上方地址栏输入网址开始浏览
browser_tabs_manage	标签页管理
browser_empty_click_hint	点击上方输入网址开始浏览
browser_night_mode_on	关闭夜间模式
browser_night_mode_off	开启夜间模式
browser_devtools	开发者工具
browser_dialog_alert_title	网页提示
browser_dialog_confirm_title	网页确认
browser_dialog_prompt_title	网页输入
browser_menu	更多设置
browser_menu_title	浏览器设置与工具
browser_menu_general_section	页面与工具
browser_night_mode_title	夜间模式
browser_night_mode_desc	降低页面亮度并启用深色偏好
browser_devtools_title	开发者工具
browser_devtools_desc	开启移动端 Eruda 调试控制台
browser_ua_section	浏览器标识 (User-Agent)
browser_ua_desc	模拟不同设备与环境访问网页
browser_ua_default	系统默认
browser_ua_default_desc	当前设备原生 WebView 标识
browser_ua_android_phone	安卓手机
browser_ua_android_phone_desc	Chrome Mobile (Android)
browser_ua_android_tablet	安卓平板
browser_ua_android_tablet_desc	Chrome Tablet (Android)
browser_ua_desktop_pc	桌面电脑
browser_ua_desktop_pc_desc	Chrome Desktop (Windows PC)
browser_ua_iphone	苹果 iPhone
browser_ua_iphone_desc	Mobile Safari (iOS)
browser_ua_ipad	苹果 iPad
browser_ua_ipad_desc	Tablet Safari (iPadOS)
browser_ua_custom	自定义
browser_ua_custom_desc	手动指定 User-Agent 字符串
browser_ua_custom_dialog_title	自定义 User-Agent
browser_ua_custom_input_hint	输入完整的 User-Agent 字符串
browser_ua_applied_toast	已切换浏览器标识并刷新页面
chat_upload_video	上传视频
chat_upload_package	上传应用安装包
about_upstream	上游项目 AiCode
about_releases	更新日志
about_feedback	反馈问题
market_empty	没有找到匹配的条目
market_expand_detail	点击展开详情
market_install	安装
market_install_failed_download	下载失败：%1${'$'}s
market_install_failed_install	安装失败：%1${'$'}s
market_install_failed_no_skill	压缩包中没有找到 SKILL.md
market_install_no_url	没有下载地址
market_install_success	安装成功：%1${'$'}s
market_installed	已安装
market_installing	正在安装 %1${'$'}s…
market_load_more	加载更多
market_loading_more	加载中…
market_matched	 · 匹配 %1${'$'}d 个
market_refresh	刷新
market_search_hint	搜索（名称 / 描述 / 作者）
market_sort_by_name	按名称
market_sort_by_stars	按星数
market_total_skills	共 %1${'$'}d 个技能
skill_detail_author	作者
skill_detail_category	分类
skill_detail_display_name	显示名
skill_detail_download	下载地址
skill_detail_name	名称
skill_detail_package	包名
skill_detail_stars	星数
skill_detail_tags	标签
skill_detail_version	版本
skill_install_note	安装会把压缩包里的 SKILL.md 提取到 ~/.aicode/skills/&lt;名字&gt;/
skill_market_title	技能市场
editor_save	保存
plugin_market_title	插件
plugin_market_section	可安装插件
plugin_installed_section	已安装
plugin_install	安装
plugin_install_url	从 URL 安装
plugin_installed	已安装
plugin_search_hint	搜索插件…
plugin_empty	暂无可显示的插件
plugin_by	作者：%1${'$'}s
plugin_enable	启用
plugin_disable	禁用
plugin_delete_title	卸载插件
plugin_delete_message	确定要卸载“%1${'$'}s”吗？相关技能也会被删除。
plugin_runtime_failed	插件运行时执行失败
plugin_tool_not_authorized	插件已停用，工具不再可用
plugin_tool_not_found	工具不可用
plugin_tool_not_found_hint	插件可能已停用、卸载或不再提供这个工具。
plugin_tool_permission_details	插件：%1${'$'}s\n工具：%2${'$'}s\n参数：%3${'$'}s
plugin_tool_permission_summary	AI 请求调用插件“%1${'$'}s”的工具“%2${'$'}s”。
plugin_tool_permission_title	确认调用插件工具
plugin_tool_unavailable	插件已停用，工具不可用
plugin_web_page_failed	页面加载失败
plugin_web_page_retry	重试
plugin_detail_title	插件详情
plugin_page_unsupported	该插件页面需要浏览器引擎，桌面端暂不支持。
plugin_store_title	插件市场
plugin_store_refresh	刷新
plugin_store_install_success	插件安装成功：%1${'$'}s
plugin_update_available	可更新至 v%1${'$'}s
plugin_up_to_date	已是最新版本
plugin_update	更新
plugin_install_resolving	正在解析版本…
plugin_install_downloading	正在下载插件包…
plugin_install_installing	正在安装…
plugin_install_progress	已下载 %1${'$'}d%%
""".trimIndent()

private val EN_TABLE: String = """
app_name	VantaCode
common_tool_missing_argument	Missing parameter: %1${'$'}s.
plan_mode_invalid_action	Invalid action: %1${'$'}s. Supported values: enter or exit.
plan_mode_already_active	Already in PLAN mode.
plan_mode_not_active	Cannot exit PLAN: the session is not in PLAN mode.
plan_mode_session_required	No session is associated with this request. Cannot switch modes.
plan_mode_session_missing	Session not found. Cannot switch modes.
plan_mode_goal_active	A goal is running autonomously. Entering or exiting PLAN is unavailable.
plan_mode_entered	Entered PLAN mode.
plan_mode_exited	Exited PLAN and restored %1${'$'}s mode.
plan_mode_enter_action	Enter PLAN mode
plan_mode_exit_action	Exit PLAN mode
plan_mode_reason_unspecified	No reason provided
plan_mode_refinement_requested	The user chose to refine the plan. Remain in PLAN and wait for specific feedback before making changes or requesting to exit again.
plan_mode_approved_build	The plan was approved. BUILD mode is active; implement within the approved scope.
plan_mode_approved_auto	The plan was approved and AUTO mode restored. Implement within the approved scope and follow AUTO permission rules.
plan_mode_external_build	The user switched to BUILD. Continue within the current task scope and mode permissions.
plan_mode_external_auto	The user switched to AUTO. Continue within the current task scope and mode permissions.
plan_mode_request_title	Switch planning mode
plan_mode_request_summary	Request to %1${'$'}s
plan_mode_request_details	Action: %1${'$'}s\n\nReason: %2${'$'}s
tool_plan_mode_denied	This operation was not executed because PLAN mode allows read-only exploration. To implement changes, submit the plan and request to exit PLAN.
tool_permission_rule_denied	Permission rules prohibit this tool call.
tool_command_rule_denied	Permission rules prohibit this command.
tool_command_rule_denied_detail	Permission rules prohibit this command. Matched rule: %1${'$'}s; offending segment: %2${'$'}s; suggestion: rewrite it in an allowed form, or ask the user to update the rule under Tool permissions and retry.
tool_command_unanalyzable_reason	The command contains constructs that cannot be statically analyzed (%1${'$'}s); it can only be allowed once and cannot be remembered.
tool_shizuku_confirmation_reason	Shizuku operates on Android as adb shell. Each call requires confirmation and cannot be saved as always allowed.
tool_vision_images_attached	Attached %1${'$'}d images for direct viewing and analysis.
tool_vision_focus	Focus: %1${'$'}s
tool_vision_empty_response	The vision model returned no analysis.
tool_vision_default_single	Describe the key information in the image, including legible text, elements, and layout. Note anything unclear.
tool_vision_default_multiple	Describe the key information in each of these %1${'$'}d images in order, then compare their similarities and differences. Note anything unclear.
tool_mcp_stdio_added	Added local MCP server %2${'$'}s (%1${'$'}s). The configuration takes effect in the next session; install any required runtime separately.
tool_mcp_invalid_name	MCP server names may contain only ASCII letters, digits, underscores, and hyphens. Current value: %1${'$'}s.
tool_memory_saved	Saved memory “%1${'$'}s” (%2${'$'}s). Its summary will be available in later sessions; use read for the full text.
tool_subagent_message_delivered	Message delivered. A running subagent will receive it shortly; an idle subagent will resume to process it.
tool_subagent_plan_readonly_warning	PLAN mode is active: subagents inherit read-only permissions, so file edits and commands will be blocked. If implementation is needed, request to exit PLAN before delegating.
tool_subagent_tier_pinned	This subagent pins its model (%1${'$'}s), so the requested "%2${'$'}s" tier was ignored.
tool_subagent_tier_fallback	The requested "%1${'$'}s" tier is not configured or its provider is unavailable; using the "%2${'$'}s" tier (%3${'$'}s) instead.
tool_subagent_tier_unavailable	The requested "%1${'$'}s" tier is not configured or its provider is unavailable; this subagent will inherit the current session model.
tool_subagent_provider_unresolved	The provider "%1${'$'}s" in this subagent definition was not found (missing or name mismatch); the parent session provider will be combined with the definition model "%2${'$'}s", which may not be callable.
settings_subagent_tiers_header	Subagent model tiers
settings_subagent_tier_light	Light
settings_subagent_tier_standard	Balanced
settings_subagent_tier_strong	Strong
settings_subagent_tier_unconfigured	Not configured
subagent_dispatch_title	Subagent Tasks
subagent_dispatch_count	%1${'$'}d tasks dispatched
subagent_card_default_agent	Subagent
subagent_card_status_dispatched	Dispatched
subagent_card_status_running	Running
subagent_card_status_completed	Completed
subagent_card_status_failed	Failed
subagent_card_status_stopped	Stopped
subagent_card_expand	Show full summary
subagent_card_collapse	Collapse summary
subagent_card_changed_files	%1${'$'}d files changed
subagent_card_open	Open session
subagent_card_stop	Stop
tool_browser_screenshot_failed	A screenshot is currently unavailable. Try again later, or use getText/getHtml to read the page.
tool_browser_condition_met	Condition met.
tool_browser_wait_timeout	Timed out before the condition was met.
tool_output_saved_hint	Full output saved to %1${'$'}s. Use readFile to read it in sections.
tool_output_unsaved_hint	Saving the full output failed. Only the beginning and end are available; try again with a smaller output range.
tool_output_omitted	[Omitted %1${'$'}d characters from the middle. %2${'$'}s]
chat_context_usage_estimated	Estimated input budget usage: %1${'$'}d%%
agent_input_budget_exceeded	DesktopContext exceeds the model input budget. Shorten the input or reduce tool definitions.
settings_title	Settings
settings_select_section_hint	Pick a setting from the list
settings_providers	AI Vendors
settings_provider_editor	Vendor
settings_default_models	Default Models
settings_general	Preferences
settings_general_models	Models
settings_auto_remove_stale_models	Remove stale models after fetching
settings_auto_remove_stale_models_desc	On a successful fetch, drop local models the vendor no longer offers
settings_general_session	Session
settings_startup_session	Session on launch
settings_startup_session_new	New session
settings_startup_session_new_desc	Reuse an empty session, otherwise start a new one
settings_startup_session_recent	Recent session
settings_startup_session_recent_desc	Open the most recently updated session
settings_delete_external_workspace_sessions	Delete external workspace chats
settings_delete_external_workspace_sessions_desc	Also delete a workspace's chats when an external local workspace is removed
settings_general_network	Network
settings_first_byte_timeout	First byte timeout
settings_first_byte_timeout_desc	Max wait for the first response chunk
settings_stream_idle_timeout	Chunk idle timeout
settings_stream_idle_timeout_desc	Max wait between response chunks
settings_timeout_unlimited	Unlimited
settings_timeout_seconds	%1${'$'}d s
settings_timeout_input_hint	Seconds; 0 means unlimited
settings_max_network_retries	Retry attempts
settings_max_network_retries_desc	Exponential-backoff retries after a network failure
settings_retries_count	%1${'$'}d
settings_retries_input_hint	0 disables retries
settings_general_input	Input
settings_enter_to_send	Enter key sends message
settings_enter_to_send_desc	When off, Enter inserts a newline and you send with the button
settings_compaction_threshold	Auto-compaction threshold
settings_compaction_threshold_desc	Compact context once it reaches this share of the window
settings_percent_value	%1${'$'}d%%
settings_compaction_threshold_input_hint	Integer percentage between 1 and 100
settings_general_tools	Tools
settings_sendfile_max_size	sendFile max file size
settings_sendfile_max_size_desc	Per-file size cap; exceeding it fails the whole batch
settings_mb_value	%1${'$'}d MB
settings_sendfile_max_size_input_hint	MB, an integer greater than 0
settings_general_goal	Goal
settings_goal_max_turns	Max continuation turns
settings_goal_max_turns_desc	Stop the autonomous loop after this many turns
settings_turns_value	%1${'$'}d turns
settings_goal_max_turns_input_hint	Integer number of turns greater than 0
settings_goal_max_tokens	Token budget
settings_goal_max_tokens_desc	Cap on input+output tokens consumed by the autonomous loop
settings_tokens_value	%1${'$'}d
settings_goal_max_tokens_input_hint	Integer not less than 1000
settings_vision_model	Vision Model
settings_compaction_model	Compaction Model
settings_title_model	Title Model
settings_image_gen_model	Image Generation Model
settings_mcp	MCP Servers
settings_skills	Skills
settings_container	Container & Images
settings_log_no_files	No log files yet
settings_log_read_failed	Failed to read log: %1${'$'}s
settings_models_fetch_failed	Fetch failed
settings_log	Log
settings_permissions	Tool Permissions
settings_remote_servers	Connections & Sync
settings_shizuku	Shizuku
settings_shizuku_status_not_installed	Not installed
settings_shizuku_status_not_running	Service not running
settings_shizuku_status_denied	Not authorized
settings_shizuku_status_ready	Ready
settings_shizuku_hint_not_installed	Tap to download and install Shizuku
settings_shizuku_hint_not_running	Tap to open Shizuku and start the service
settings_shizuku_hint_denied	Tap to request Shizuku permission
settings_shizuku_hint_ready	Authorized — AI can run commands via adb shell
settings_backup	Backup & Restore
settings_about	About
settings_language	Language
settings_token_stats_title	Token Stats
settings_token_stats_period_today	Today
settings_token_stats_period_7d	7 days
settings_token_stats_period_30d	30 days
settings_token_stats_period_all	All
settings_token_stats_total	Total
settings_token_stats_requests	Requests
settings_token_stats_cost	Cost
settings_token_stats_cache_hit_rate	Cache hit rate
settings_token_stats_cache_read_write	Read %1${'$'}s / Write %2${'$'}s
settings_token_stats_trend	Usage trend
settings_token_stats_legend_input	Input
settings_token_stats_legend_output	Output
settings_token_stats_legend_cached	Cached
settings_token_stats_provider	By vendor
settings_token_stats_model	By model
settings_token_stats_success_rate	Success rate %1${'$'}s
settings_token_stats_recent_calls	Call records
settings_token_stats_unknown	Unknown
settings_token_stats_no_data	No data
settings_token_stats_calls_suffix	calls
settings_token_stats_calls_suffix2	%1${'$'}d calls
settings_token_stats_col_model	Model
settings_token_stats_col_reasoning_effort	Effort
settings_token_stats_col_reasoning_wire	Sent as
settings_token_stats_col_provider	Vendor
settings_token_stats_col_input	Input
settings_token_stats_col_output	Output
settings_token_stats_col_cached_read	Cache read
settings_token_stats_col_cached_write	Cache write
settings_token_stats_col_cache_hit_rate	Hit rate
settings_token_stats_col_ttfb	TTFB
settings_token_stats_col_duration	Duration
settings_token_stats_col_time	Time
settings_token_stats_col_status	Status
settings_token_stats_col_cost	Cost
settings_token_stats_page	Page %1${'$'}d of %2${'$'}d
settings_token_stats_retries	%1${'$'}d retries
settings_token_stats_reset	Reset
settings_token_stats_reset_title	Reset token stats
settings_token_stats_reset_confirm	All call records will be permanently deleted. Reset?
settings_token_stats_filter_all_providers	All Vendors
settings_token_stats_filter_all_models	All Models
settings_token_stats_filter_clear	Clear filters
settings_token_stats_filter_provider	Vendor: %1${'$'}s
settings_token_stats_filter_model	Model: %1${'$'}s
settings_token_stats_provider_keys	%1${'$'}d keys
settings_token_stats_requests_unit	requests
settings_storage	Storage
storage_refresh	Rescan
storage_total_title	Used by AiCode
storage_scanning	Calculating…
storage_device_free	%1${'$'}s free of %2${'$'}s
storage_section_breakdown	Breakdown
storage_breakdown_note	Tap a category with an arrow to expand details. Delete sessions to shrink chat history; reset or remove containers and workspaces from their own pages. External folder workspaces live on device storage and are not counted.
storage_freed	Freed %1${'$'}s
storage_clean	Clean
storage_clean_selected	Clean selected · %1${'$'}s
storage_clean_confirm_title	Confirm cleanup
storage_clean_confirm_selected	The selected items below will be deleted (about %1${'$'}s). This cannot be undone.
storage_clean_scope	Only selected local app caches, logs and data in global directories are cleaned. This may affect historical output access or image follow-ups across multiple workspaces. Chat history, workspace files, containers, AI configuration and remote server data are not deleted.
storage_category_chat	Chat history
storage_category_container	Containers
storage_category_container_images	Container images
storage_category_workspaces	Workspaces
storage_category_ai_config	AI config & output
storage_category_checkpoints	Checkpoints
storage_category_logs	Logs
storage_category_caches	Caches
storage_category_other	Other data
storage_category_apk	App package
storage_clean_caches	Cached files
storage_clean_caches_desc	Temporary files, rebuilt automatically
storage_clean_logs	Log files
storage_clean_logs_desc	App and AI request logs; deleting them affects nothing
storage_clean_tool_output	Tool output archive
storage_clean_tool_output_desc	Saved copies of oversized tool output; output paths in older messages stop resolving
storage_clean_vision	Vision session cache
storage_clean_vision_desc	History for image follow-up questions; old sessions can no longer be continued
storage_untitled_session	Untitled session
storage_chat_messages	%1${'$'}d messages
storage_workspace_attachments	incl. attachments %1${'$'}s
storage_logs_app	App logs
storage_logs_ai	AI request logs
language_follow_system	Follow System
common_back	Back
common_scroll_to_bottom	Back to bottom
common_prev	Previous
common_next	Next
settings_add_provider	Add vendor
settings_reconnect	Reconnect
settings_add_mcp_server	Add MCP server
settings_refresh_logs	Refresh logs
settings_vision_follow_chat	Follow current chat model
settings_compaction_follow_chat	Follow current chat model
settings_title_follow_chat	Follow current chat model
settings_image_gen_unconfigured	Not configured
settings_theme_title	Appearance
theme_auto	Auto
theme_dark	Dark
theme_light	Light
theme_mode_section	Appearance mode
theme_preset_section	Color theme
theme_preset_default	Default Blue
theme_preset_coffee	Coffee
theme_preset_graphite	Graphite
theme_preset_forest	Forest
theme_dynamic_color	Dynamic color
theme_dynamic_color_desc	Derive colors from your wallpaper. Overrides the color theme above.
theme_dynamic_color_unsupported	Requires Android 12 or newer
settings_background_image	Background image
settings_background_image_none	None
settings_background_image_alpha	Opacity
settings_background_image_pick	Pick image
settings_background_image_remove	Remove background
team_member_add	Add member
team_editor_add_agent	Add from subagents
team_editor_add_model	Add from models
settings_background_kind_gif	GIF background
settings_background_kind_video	Video background
settings_teams	Teams
chat_open_teams	Teams
teams_new	New team
teams_empty	No teams yet
teams_empty_hint	Gather subagents into a team and let them talk it through
teams_list_header	Teams
teams_member_count	%1${'$'}d members
team_editor_new	New team
team_editor_edit	Edit team
team_editor_basic	Basics
team_editor_owner	Owner
team_editor_owner_name	Your display name
team_editor_pick_avatar	Tap to pick an avatar
team_editor_members	Members
team_editor_members_hint	Members are enabled subagents; you can pin a model + one-line duty
team_editor_rename_member	Member name
team_editor_remove_member	Remove from team
team_editor_autocompose	Auto-compose
team_editor_autocompose_action	Let AI assign roles
team_editor_autocompose_placeholder	e.g. frontend focused, must review code (optional)
team_editor_autocomposing	Composing…
team_editor_no_agents	No subagents yet — create one first
team_editor_no_models	No models configured — add one under AI Vendors
team_editor_no_members	No members yet — pull in a subagent or a model
team_member_lead	Lead
team_member_prompt_hint	One line: what does this member do
team_name_hint	e.g. Frontend crew
team_description_hint	What this team is good at; the main agent uses it to decide when to dispatch it
team_strategy_hint	Who does what, in which order, and how results are handed over
team_error_invalid_name	Invalid or taken team name
team_error_name_conflict	A team with this name already exists
team_error_member_limit	Too many members (max %1${'$'}d)
team_error_no_members	Pick at least one member
team_delete	Delete team
team_delete_confirm	Delete team "%1${'$'}s"? Its chat history will be removed too.
team_chat_title	Group chat
team_chat_input_hint	Say something to the group
team_chat_send	Send
team_chat_stop	Stop
team_chat_thinking	Members are discussing…
team_chat_empty	Nobody has spoken yet
team_chat_empty_hint	%1${'$'}s, kick it off — members will follow
team_chat_clear	Clear history
team_chat_clear_confirm	Clear all messages in this team chat?
provider_cache_anthropic_title	Anthropic cache breakpoints
provider_cache_anthropic_subtitle	Mark system/tools/messages prefixes with explicit cache breakpoints to cut repeated input cost
provider_cache_openai_chat_title	OpenAI-compatible cache key
provider_cache_openai_chat_subtitle	Send prompt_cache_key with requests
provider_section_dashboard	Custom Dashboard
provider_dashboard_script	Dashboard Script
provider_dashboard_script_placeholder	e.g. demo_balance.py
provider_dashboard_select_script	Select Script
provider_dashboard_script_desc	Execute and preview dashboard output
provider_dashboard_test_btn	Run Test
provider_dashboard_run_test	Run Test
provider_dashboard_preview_title	Dashboard Preview
provider_test_detail_title	Test Debug Details
provider_test_req_url	Request URL
provider_test_req_headers	Request Headers
provider_test_req_body	Request Body
provider_test_resp_headers	Response Headers
provider_test_resp_body	Response Body
provider_test_error_detail	Error Stack
provider_dashboard_test_failed	Execution Failed
provider_dashboard_no_scripts	No scripts found in ~/.aicode/scripts/
provider_dashboard_select_title	Select Dashboard Script
provider_dashboard_clear_script	None (Clear script)
provider_dashboard_loading	Loading dashboard...
provider_dashboard_raw_output	Raw Output
provider_script_params_title	Script Parameters
provider_script_params_desc	Injected into the panel script as AICODE_KEY_ environment variables
provider_script_params_empty	No parameters yet. Tap below to add
provider_script_param_key	Parameter name (KEY)
provider_script_param_key_hint	e.g. ACCOUNT_ID
provider_script_param_value	Parameter value
provider_script_param_value_hint	e.g. acc_12345
provider_script_param_add	Add parameter
provider_script_param_remove	Remove parameter
provider_script_param_manage	Manage
provider_script_params_count	%1${'$'}d parameters configured
provider_add_sheet_title	Add Vendor
provider_custom	Custom Vendor
provider_preset_loading	Loading presets…
provider_preset_empty	No matching presets
provider_register_now	Register
settings_keepalive_title	Background keepalive
settings_keepalive_subtitle	Foreground notification prevents background kill
settings_screen_on_title	Keep screen on
settings_screen_on_subtitle	Screen stays awake while the app is in the foreground
settings_network_guide_title	Background network troubleshooting
settings_network_guide_subtitle	Connection dropping in background? Check these
settings_network_guide_intro	If you see "Network connection lost" after switching to the background, set up in this order:
settings_network_guide_step_keepalive	1. Turn on "Background keepalive" above and keep its notification
settings_network_guide_step_lock	2. Lock this app in the Recents screen instead of swiping it away
settings_network_guide_step_battery	3. In system settings, set this app's battery policy to "Unrestricted / Don't optimize" and allow auto-launch
settings_network_guide_brand_huawei	Huawei / Honor: App launch → Manage manually → Auto-launch, Associated launch, Run in background
settings_network_guide_brand_xiaomi	Xiaomi / Redmi: Battery and performance → App battery saver → No restrictions
settings_network_guide_brand_oppo	OPPO / OnePlus / realme: App management → Battery usage → Allow background activity; Battery → More settings → Optimize battery use → Don't optimize
settings_network_guide_brand_vivo	vivo / iQOO: Battery → Background power consumption management → Allow high background power; Apps and permissions → Auto-start
settings_network_guide_brand_samsung	Samsung: Battery and device care → Battery → Background usage limits → Never sleeping apps; Apps → Battery → Unrestricted
settings_network_guide_more	See the online docs for more devices and details
settings_agent_sound_title	Agent completion notification
settings_agent_sound_subtitle	Pop a notification when agent finishes in background
settings_live_notification_title	Live notifications
settings_live_notification_subtitle	Show live progress notifications while the AI works in the background
settings_approval_notification_title	Approval notifications
settings_approval_notification_subtitle	Notify when the AI requests permission, asks a question, or waits for plan approval
agent_complete_notification_title	Task complete
agent_complete_notification_body	Tap to view
agent_complete_notification_background_body	Background task "%1${'$'}s" finished
agent_complete_notification_background_multi_body	%1${'$'}d background tasks finished
notification_channel_terminal_service	Terminal Services
notification_channel_terminal_service_desc	Notifications for background terminal tasks
notification_keepalive_persistent_sessions	Keepalive · terminal tasks: %1${'$'}d
notification_keepalive_running_sessions	Running terminal tasks: %1${'$'}d
notification_keepalive_enabled	Keepalive enabled
notification_channel_agent_complete	Agent completion notifications
notification_channel_agent_complete_desc	Notifications shown when the agent finishes in background
notification_channel_agent_live	AI live progress
notification_channel_agent_live_desc	Notifications showing the current progress while the AI works in the background
notification_agent_live_title_multi	%1${'$'}d tasks running
notification_channel_agent_approval	Approval notifications
notification_channel_agent_approval_desc	Notifications shown when the AI requests permission, asks a question, or waits for plan approval in the background
notification_approval_title	AI is waiting for your confirmation
notification_approval_multi_title	%1${'$'}d tasks awaiting confirmation
notification_approval_needed	Approval needed
notification_approval_label_permission	Permission required
notification_approval_label_question	Answer required
notification_approval_label_plan	Plan approval
notification_approval_action_allow	Allow
notification_approval_action_reject	Reject
notification_approval_action_approve_plan	Approve
settings_app_permissions	App Permissions
settings_permission_apk_install	Install unknown apps
settings_permission_apk_install_desc	Allow installing APK packages
settings_permission_storage	Storage access
settings_permission_storage_desc	Read/write workspace files and container images
settings_permission_battery	Ignore battery optimization
settings_permission_battery_desc	Keep running in background to reduce process kill
settings_autostart_title	Auto-start management
settings_autostart_subtitle	Allow the system to restart the app in background
settings_permission_granted	Allowed
settings_permission_denied	Not allowed
settings_permission_notification_title	Notification permission required
settings_permission_notification_denied_desc	Background keepalive and agent completion notifications need the notification permission, but the system prompt cannot be shown. Please enable it manually in system settings
settings_permission_storage_denied_desc	Storage permission was denied and can no longer be requested. Please enable it manually in system settings
settings_category_ai	AI Configuration
settings_category_environment	Environment
settings_category_permissions	Permissions & Background
settings_category_general	General
settings_category_data	Data & Diagnostics
settings_category_help	Help & About
settings_category_background	Background
settings_category_system_permissions	System permissions
settings_user_guide	User Guide
common_ai_providers	AI Vendors
common_save	Save
common_done	Done
common_copy	Copy
common_copy_success	Copied to clipboard
common_cut	Cut
common_paste	Paste
common_allow	Allow
common_close	Close
common_clear	Clear
common_more	More
workbench_split_handle	Drag to resize panes
common_other	Other
common_switch	Switch
common_create	Create
common_delete	Delete
common_cancel	Cancel
common_retry	Retry
common_confirm	Confirm
common_go_to_settings	Go to Settings
common_name	Name
common_image_preview	Image Preview
common_image_load_failed	Failed to load image; the file may have been moved or deleted
common_expand	Expand
common_workspace	Workspace
common_tool	Tool
common_file	File
common_collapse	Collapse
common_reset	Reset
common_collapse_action	Collapse
chat_new_session	New Session
common_new_tab	New Tab
common_show	Show
common_local	Local
common_model	Model
common_label_with_api	%1${'$'}s (%2${'$'}s)
common_add	Add
credential_add	Add Credential
common_username	Username
common_got_it	Got it
common_edit	Edit
common_enabled	Enabled
common_disabled	Disabled
credential_token	Access Token / PAT
credential_auto_inject_title	Auto-inject credentials
credential_auto_inject_hint	Sync git credentials saved in the app to this remote server, effective only for repositories under the current workspace root. Does not affect your manual git usage on the server.
credential_auto_inject_prompt_title	Auto-inject git credentials?
credential_auto_inject_prompt_body	Allow AiCode to sync git credentials saved in the app to this remote server? It only applies to repositories under the current workspace root and will not modify the server's global git config.
credential_auto_inject_enable	Enable
common_rename	Rename
common_hide	Hide
log_files	Log Files
log_mcp_prefix	MCP Log: %1${'$'}s
log_no_match	No logs match the current filter
log_search_hint	Search logs
log_search_results	%1${'$'}s matches
log_show_last_lines	Showing last %1${'$'}s/%2${'$'}s lines
log_show_lines	Showing %1${'$'}s lines
log_copy	Copy
log_copied	Copied
log_clear_filter	Clear filter
log_copy_menu_title	Copy Log
log_copy_line	Copy this line
log_copy_with_stack	Copy with stack trace
log_copy_message	Copy message
log_follow_latest	Live
sync_unknown_connection	Unknown connection
chat_attachment_prefix	Attachments:
chat_attachment_max	Up to %1${'$'}s attachments at once
chat_attachment_uploaded	%1${'$'}s attachments uploaded
chat_attachment_uploaded_partial	%1${'$'}s attachments uploaded, some were not added
chat_image_too_large	Image exceeds %1${'$'}s, please compress and retry
chat_attachment_uploading	Uploading %1${'$'}d files…
chat_uploaded_to	Uploaded to %1${'$'}s
chat_select_workspace_first	Please select a workspace first
chat_read_file_failed	Cannot read the selected file
chat_camera_file_failed	Cannot create camera file
chat_workspace_unavailable	Workspace unavailable
chat_upload_failed	Upload failed
chat_unsupported_image_type	Unrecognized image type, only jpg/png/gif/webp supported
chat_new_session_btn	New Chat
chat_open_sidebar	Open sidebar
chat_add_attachment	Add attachment
chat_take_photo	Take photo
chat_reasoning_effort	Reasoning effort
chat_reasoning_effort_none	None
chat_reasoning_effort_minimal	Minimal
chat_reasoning_effort_low	Low
chat_reasoning_effort_medium	Medium
chat_reasoning_effort_high	High
chat_reasoning_effort_xhigh	Extra high
chat_reasoning_effort_max	Max
chat_reasoning_toggle	Thinking
chat_reasoning_off_short	Off
chat_reasoning_default_tag	Default
chat_reasoning_effective_tag	Active
chat_reasoning_effective	Effective: %1${'$'}s
chat_reasoning_unknown_hint	Levels are unknown for this model, so the selected level may not take effect
chat_reasoning_cannot_disable	This model always thinks; the lowest level is used instead
chat_reasoning_endpoint_weak	This endpoint may ignore "None"; switch to the provider's Responses endpoint to fully disable thinking
provider_model_section_reasoning	Reasoning levels
provider_model_reasoning_hint	Tick only the levels this model really accepts; leave all unticked to use the built-in capability table
provider_model_reasoning_can_disable	Thinking can be turned off
chat_open_file_missing	File not found or has been moved
chat_open_file_unshareable	This file's location cannot be shared with other apps
chat_open_file_no_app	No app available to open this file
chat_open_apk_permission	Installing apps requires permission. Allow installs from this source in the settings that just opened
chat_attachment_preview	Preview %1${'$'}s
chat_attachment_open	Open %1${'$'}s
chat_no_model_selected	No model selected
chat_open_git	Open Git
chat_open_terminal	Open terminal
chat_open_browser	Open browser
chat_ssh_connected	SSH: Connected
chat_ssh_connecting	SSH: Connecting…
chat_ssh_failed	SSH: Connection failed
chat_ssh_disconnected	SSH: Not connected
chat_connecting_remote	Connecting to remote server…
chat_remote_connect_failed	Remote server connection failed, please check settings
chat_no_remote_connection	Not connected to remote server
chat_placeholder	How can I help you?
chat_input_hint	Describe what you need, and I'll help you write, modify, or explain code
agent_stopped_by_user	Stopped by user
agent_tool_executing	%1${'$'}s running…
agent_stop_refusal	The model declined to answer under its content safety policy.
agent_stop_context_exceeded	This request exceeds the model's context window. Compact the context or start a new session.
agent_context_no_compaction	No compaction needed (too few messages)
agent_compaction_failed	Compaction failed: %1${'$'}s
agent_model_not_selected	Not selected
about_version	Version
about_github_repo	GitHub Repository
about_license	Open Source License
about_description	VantaCode · Universal AI coding agent for desktop · local shell & remote SSH
about_check_update	Check for updates
about_checking_update	Querying GitHub for the latest version…
about_up_to_date	Already up to date
about_up_to_date_detail	Current version v%1${'$'}s, already the latest Release on GitHub.
about_new_version_found	New version available
about_download	Download
about_later	Later
about_check_failed	Check failed
about_ok	OK
about_qq_group	QQ Group
about_qq_group_copied	Group number copied
about_parse_version_failed	Cannot parse the latest version number
about_network_error	Network error
about_auto_check_update	Auto check for updates
about_auto_check_update_desc	Check for new versions when the app opens
about_update_channel	Update Channel
update_channel_stable	Stable
update_channel_stable_desc	Stable releases only
update_channel_latest	Preview
update_channel_latest_desc	Also detects pre-releases (RC)
about_no_changelog	No changelog available
about_update_mirror	Download mirror
about_update_mirror_desc	Speeds up update downloads; leave empty to use GitHub directly
about_update_mirror_preset_cdn	gh-proxy CDN (default)
about_update_mirror_preset_v6	gh-proxy preferred node
about_update_mirror_preset_direct	GitHub direct
about_update_mirror_invalid	Address must start with http:// or https://
about_update_mirror_default	Restore default
about_update_download_install	Download & install
about_update_install	Install
about_update_browser	Download in browser
about_update_background	Continue in background
about_update_downloading	Downloading %1${'$'}d%%
about_update_download_size	%1${'$'}s / %2${'$'}s
about_update_apk_ready	Update package is ready — tap Install to finish
about_update_need_permission	Allow “install unknown apps” first, then come back and tap Install
about_update_download_failed	Download failed
about_update_download_failed_hint	Change the mirror in Settings → About → Download mirror and retry
about_update_retry	Retry
about_update_verify_not_apk	The downloaded file is not a valid package
about_update_verify_package	The package does not match this app
about_update_verify_signature	Package signature differs from this app — download in browser instead
about_update_verify_not_newer	Package version is not newer than the installed one
about_update_install_failed	No app available to install packages
agreement_title	Welcome to AiCode
agreement_terms	User Agreement
agreement_privacy	Privacy Policy
agreement_notice_prefix	"Before using AiCode, please read and agree to the "
agreement_terms_link	User Agreement
agreement_notice_conjunction	" and "
agreement_privacy_link	Privacy Policy
agreement_notice_suffix	" to understand the usage rules and data handling practices.\n\nBy tapping “Agree and continue”, you confirm that you have read and agree to both documents."
agreement_accept	Agree and continue
agreement_decline	Disagree
agreement_terms_url	https://aicode.murk.top/en/terms
agreement_privacy_url	https://aicode.murk.top/en/privacy
ask_confirm_title	AI needs your confirmation
ask_supplement	Supplement
ask_confirm	Confirm
ask_custom_answer	Enter a custom answer
ask_input_hint	Please enter…
backup_exported	Backup exported
backup_write_failed	Write failed: %1${'$'}s
backup_read_failed	Failed to read backup file
backup_export_title	Export Backup
backup_export_subtitle	Select data to export, optionally password-encrypted
backup_import_title	Import Backup
backup_import_subtitle	Restore data from backup file
backup_set_password	Set export password
backup_password_hint	Leave empty for unencrypted tar.gz; enter a password for AES-GCM encryption
backup_export_btn	Export
backup_password_input	Enter backup password
backup_password_optional_hint	Leave empty if backup is unencrypted
backup_import_btn	Import
backup_operation_failed	Operation failed
backup_import_done	Import complete
backup_actions	Actions
backup_select_data	Select export data
backup_data_remote	Connections & Sync
backup_data_chat_history	Chat History
backup_data_mcp	MCP Servers
backup_data_permissions	Permission Rules
backup_data_app_settings	App Settings
backup_data_workspace	Workspace Files
backup_workspace_git_hint	Workspace files are best backed up via Git; the export here is only a fallback
backup_password_label	Password (optional)
backup_processing	Processing
backup_processing_data	Processing data…
backup_restored_data	Restored data:
backup_stat_providers	· AI Vendors: %1${'$'}s
backup_stat_remote_connections	· Remote Connections: %1${'$'}s
backup_stat_remote_mounts	· Remote Mounts: %1${'$'}s
backup_stat_chat_sessions	· Chat Sessions: %1${'$'}s
backup_stat_chat_messages	· Chat Messages: %1${'$'}s
backup_stat_todo_items	· Todo Items: %1${'$'}s
backup_stat_mcp_servers	· MCP Servers: %1${'$'}s
backup_stat_permission_rules	· Global Permission Rules: %1${'$'}s
backup_stat_workspace_files	· Workspace Files: %1${'$'}s
backup_settings_covered	Theme, keepalive, logging, vision, and sync preferences covered
backup_restore_workspaces_title	Restore workspaces
backup_restore_workspaces_warning	Files in local workspaces with the same name will be overwritten and cannot be undone. Select carefully.
backup_restore_workspaces_select_all	Select all
backup_restore_workspaces_files	%1${'$'}d files
backup_restore_workspaces_confirm	Restore
backup_export_failed	Export failed
backup_wrong_password	Wrong backup password, or the encrypted backup file is corrupted
backup_import_failed	Import failed
backup_net_section	Cloud backup
backup_tab_local	Local
backup_net_targets	Backup targets
backup_net_add_target	Add backup target
backup_net_edit_target	Edit backup target
backup_net_no_target	No cloud backup target yet
backup_net_no_target_hint	Tap + in the top right to add a WebDAV or S3 backup target
backup_net_upload	Upload
backup_net_upload_short	Upload
backup_net_upload_password_hint	The backup will be uploaded to the cloud. A password is required (AES-GCM) to keep API keys, SSH credentials, etc. from being exposed in plain text
backup_net_upload_password_required	An encryption password is required for cloud backup
backup_net_upload_subtitle	Export the current backup and upload to the selected target
backup_net_restore	Restore from cloud
backup_net_restore_subtitle	Download a backup from the selected target and restore
backup_net_test_connection	Test connection
backup_net_test_success	Connection succeeded
backup_net_test_failed	Connection failed
backup_net_upload_failed	Upload failed
backup_net_list_failed	Failed to read cloud backups
backup_net_delete_failed	Failed to delete cloud backup
backup_net_upload_done	Uploaded to %1${'$'}s
backup_net_type_webdav	WebDAV
backup_net_type_s3	Object storage (S3)
backup_net_name	Name
backup_net_endpoint	Server URL
backup_net_endpoint_hint	e.g. https://dav.example.com/dav
backup_net_base_path	Remote directory
backup_net_base_path_hint	WebDAV directory or S3 object prefix
backup_net_username	Username
backup_net_password	Password
backup_net_access_key	Access Key
backup_net_secret_key	Secret Key
backup_net_session_token	Session Token (optional)
backup_net_bucket	Bucket
backup_net_region	Region
backup_net_path_style	Path-style addressing
backup_net_remote_list_title	Cloud backups
backup_net_remote_empty	No backups in the cloud
backup_net_remote_restore	Restore
backup_net_remote_delete	Delete
backup_net_remote_delete_confirm	Delete cloud backup %1${'$'}s? This cannot be undone.
backup_net_loading	Loading cloud backups…
backup_net_uploading	Uploading backup…
backup_net_testing	Testing connection…
backup_net_delete_target_confirm	Delete backup target %1${'$'}s?
chat_history	History
chat_no_sessions_hint	No sessions yet
chat_search_hint	Search chats
chat_search_no_result	No matching messages
chat_search_clear	Clear
session_group_today	Today
session_group_yesterday	Yesterday
session_group_last_7_days	Last 7 Days
session_group_last_30_days	Last 30 Days
session_group_pinned	Pinned
session_group_month_format	MMMM yyyy
chat_settings	Settings
chat_pin_session	Pin to Top
chat_unpin_session	Unpin
chat_delete_session	Delete Session
chat_delete_session_confirm	Delete "%1${'$'}s"? All messages in this session will be cleared and cannot be undone.
chat_batch_select	Select Multiple
chat_batch_delete_title	Delete Selected Sessions
chat_batch_delete_confirm	Delete the selected %1${'$'}d sessions? All messages in these sessions will be cleared and cannot be undone.
chat_sessions_selected_count	%1${'$'}d sessions selected
chat_rename_session	Rename Session
chat_session_name	Session name
chat_export_session	Export Session
chat_export_session_done	Session exported
chat_export_session_failed	Export failed
subagent_tab_sessions	Chats
drawer_tab_files	Files
chat_collapse_sidebar	Collapse sidebar
drawer_expand_subagents	Expand subagents
drawer_collapse_subagents	Collapse subagents
file_browser_up	Up one level
file_browser_empty	Empty folder
file_browser_error	Failed to read folder
file_browser_refresh	Refresh
file_browser_set_as_workspace	Set as workspace
file_browser_expand	Expand
file_browser_collapse	Collapse
file_browser_new_file	New file
file_browser_new_folder	New folder
file_browser_name_label	Name
file_browser_delete_file_confirm	Delete file "%1${'$'}s"?
file_browser_delete_folder_confirm	Delete folder "%1${'$'}s" and all of its contents?
file_browser_create_failed	Create failed: name may already exist or the folder is not writable
file_browser_rename_failed	Rename failed: the name may already exist
file_browser_delete_failed	Delete failed
file_browser_paste_here	Paste here
file_browser_paste_conflict_title	Same name already exists
file_browser_paste_conflict_message	An item named "%1${'$'}s" already exists at the destination. Overwrite?
common_overwrite	Overwrite
file_browser_clipboard_cut	Cut
file_browser_clipboard_copy	Copy
editor_undo	Undo
editor_redo	Redo
editor_save_success	Saved
editor_save_failed	Save failed
editor_unsaved_title	Unsaved changes
editor_unsaved_message	This file has unsaved changes. Save them?
editor_discard	Discard
editor_save_and_exit	Save and exit
editor_save_and_switch	Save and switch
editor_dont_save	Don't save
editor_md_preview	Preview Markdown
editor_md_show_source	Show source
editor_indent	Indent
editor_cursor_left	Move cursor left
editor_cursor_right	Move cursor right
editor_settings	Editor settings
editor_font_size	Font size
editor_word_wrap	Word wrap
editor_show_indent_guide	Show indent guides
editor_show_wrap_arrow	Show word-wrap arrows
editor_show_whitespace	Show whitespace
editor_font_size_value	%1${'$'}d sp
editor_file_too_large	File too large (%1${'$'}d MB) to open
editor_load_failed	Failed to open file
editor_binary_unsupported	Binary file, opening not supported
chat_input_placeholder	Type a message to chat with AI…
chat_queue_hint	AI is busy, press enter to queue the message
chat_input_expand	Expand input box
chat_mention_loading	Loading files…
chat_mention_unavailable	Container not ready, cannot search files
chat_mention_error	Failed to load file list
chat_mention_empty	No matching files
chat_input_fullscreen_title	Full-screen input
chat_queue_title	Queue (%1${'$'}d)
chat_queue_remove	Remove from queue
chat_queue_edit	Edit queued message
chat_queue_interject	Insert to AI now
chat_queue_drag	Drag to reorder
chat_upload_file	Upload File
chat_upload_image	Upload Image
chat_remove_attachment	Remove attachment
chat_no_models_hint	No models available, add one in "AI Vendors"
chat_stop	Stop
chat_send	Send
chat_perm_single_use_desc	Contains command substitution/pipes/redirects, cannot be remembered, single-use only
chat_perm_always_tool_desc	"Always allow" will remember all calls of this tool for this project
chat_perm_always_prefix	"Always allow" will remember for this project:
chat_perm_deny	Deny
chat_perm_always_allow	Always Allow
chat_perm_session_label	Session "%1${'$'}s"
chat_plan_completed	Plan completed
chat_continue_feedback	Continue feedback
chat_approve_and_implement	Approve and implement
checkpoint_rewind_title	Checkpoint & Rewind
checkpoint_target_prompt	Target message: %1${'$'}s
checkpoint_restore_code_and_conversation	Restore code and conversation
checkpoint_restore_code_and_conversation_desc	Revert both code and conversation before this checkpoint
checkpoint_restore_conversation	Restore conversation only
checkpoint_restore_conversation_desc	Keep current code, truncate conversation to this point
checkpoint_restore_code	Restore code only
checkpoint_restore_code_desc	Revert code to snapshot before this point, keep conversation
checkpoint_skipped_single	Skipped 1 file: it changed after the snapshot, so it was left alone to keep those edits\n%1${'$'}s
checkpoint_skipped_multiple	Skipped %1${'$'}d files: they changed after the snapshot, so they were left alone to keep those edits\n%2${'$'}s
editor_save_conflict_title	File changed on disk
editor_save_conflict_message	This file was modified by the AI or another program after you opened it. Saving now would overwrite those changes.
editor_save_conflict_diff	- Current on disk\n+ Your edits\n\n%1${'$'}s
editor_save_conflict_overwrite	Overwrite disk
editor_save_conflict_discard	Discard my edits
editor_save_conflict_cancel	Cancel
editor_save_conflict_discarded	Edits discarded; reloaded from disk
chat_copied	Copied
chat_copy	Copy
chat_task_duration	Total time %1${'$'}s
chat_cache_hit_rate	Cache hit rate %1${'$'}s
chat_status_generating	Generating
chat_status_thinking	Thinking
chat_status_calling_tool	Calling tool
chat_status_running_command	Running command
chat_status_editing_file	Editing file
chat_status_reading_file	Reading file
chat_status_viewing_image	Viewing image
chat_status_searching_web	Searching the web
chat_status_browsing_web	Controlling browser
chat_status_asking_user	Asking user
chat_status_generating_image	Generating image
chat_status_updating_todo	Updating to-dos
chat_status_starting_subagent	Starting sub-agent
chat_status_reading_memory	Reading memory
chat_tool_running	Tool running
chat_tool_succeeded	Tool succeeded
chat_tool_failed	Tool failed
chat_tool_calls_count	%1${'$'}d tool calls
chat_turn_running	Running
chat_turn_completed	Completed in %1${'$'}s
chat_turn_completed_plain	Completed
chat_context_usage	DesktopContext %1${'$'}d%% used
chat_bg_command_done	Background command completed
chat_bg_commands_done	%1${'$'}d background tasks completed
chat_bg_commands_partial_failed	%1${'$'}d background tasks finished, %2${'$'}d failed
chat_context_compressed	DesktopContext compressed
chat_compaction_failed	DesktopContext compaction failed
chat_compressing_context	Compressing context
chat_retrying	Retrying %1${'$'}s/%2${'$'}s
chat_key_switched	Switched to key %1${'$'}d of %2${'$'}d
chat_goal_label	Goal · %1${'$'}s
chat_goal_status_active	Active
chat_goal_status_paused	Paused
chat_goal_status_completed	Completed
chat_goal_status_blocked	Blocked
chat_goal_status_exhausted	Budget exhausted
chat_goal_pause	Pause goal
chat_goal_resume	Resume goal
chat_goal_clear	Clear goal
chat_error_title	Request failed
network_error_dns	DNS resolution failed: the host has no usable address. Check your network, Wi-Fi DNS, or proxy settings
network_error_timeout	Request timed out: slow network or no response from the server
network_error_connect	Connection refused: check the Base URL and port
network_error_ssl	SSL handshake failed: check the certificate or proxy settings
common_error_unknown	Unknown error
slash_command_compress_desc	Manually compact this session context
slash_command_init_desc	Analyze the workspace codebase and generate or improve the AGENTS.md project rules
slash_command_usage_desc	View today and all-time call count, token usage and estimated cost
slash_command_goal_desc	Set a session goal; the AI loops autonomously until done or the budget runs out
retry_error_rate_limit	Rate limit
retry_error_server_overloaded	Server overloaded
retry_error_server	Server error
retry_error_network	Network disconnected
retry_error_timeout	Connection timed out
retry_error_connection_refused	Connection refused
retry_error_dns_failed	DNS resolution failed
retry_error_connection_reset	Connection interrupted
retry_error_ssl	SSL handshake failed
retry_error_unknown	Network error
retry_error_with_code	%1${'$'}s (%2${'$'}d)
chat_thinking_process	Thinking process
chat_thinking_running_time	Thinking (%1${'$'}ss)
chat_thinking_done_time	Thought for %1${'$'}ss
chat_thinking_done	Thought
container_reset	Reset
container_add_image	Add Image
container_remote_ssh	Remote SSH
container_custom_image	Custom Image
container_delete_config	Delete Image Config
container_delete_confirm	Delete "%1${'$'}s"? %2${'$'}s
container_rootfs_will_be_cleared	Its rootfs directory will also be removed.
container_builtin_restorable	Built-in images can be restored from the empty list.
container_switch_image	Switch Container & Images
container_switch_confirm	After switching to "%1${'$'}s", the currently running AI sessions will be stopped and terminal tabs will be closed. Continue?
container_reset_title	Reset Container
container_resetting_title	Cleaning Container Data
container_resetting_message	Deleting data for "%1${'$'}s"…\n%2${'$'}s items removed
container_reset_confirm	This will delete the rootfs directory of "%1${'$'}s" (including installed tools and configs). It will be re-extracted and initialized on next use. Continue?
container_builtin_badge	Built-in
container_custom_badge	Custom
container_ssh_badge	SSH
container_empty	No containers or images
container_empty_desc	Add a custom image, or restore the built-in Alpine.
container_restore_builtin	Restore Built-in Alpine
container_set_as_default	Set as default container
container_default_badge	Default
container_default_desc	In remote workspace mode, local MCP services run in this container
container_shell_desc	Shell: %1${'$'}s
container_edit_image	Edit Image
container_local_image	Local Image
container_shell_path	Shell
container_extra_bindings	Mounts
container_extra_proot_args	Proot args
container_importing	Copying image file…
container_not_ready_hint	Container is not initialized yet. Please open the Terminal page to complete initialization first.
container_file_selected	File selected
container_select_image_file	Select tar.gz / tgz / tar.xz / txz image file
container_sort_long_press	Long press to reorder
container_mount_local	Local path
container_mount_container	Container path
container_no_bindings	No mounts
container_add_binding	Add mount
container_arg_value	Arg value
container_no_args	No args
container_add_arg	Add arg
container_env_vars	Environment variables
container_env_name	Variable name
container_env_value	Variable value
container_no_env_vars	No custom environment variables
container_add_env_var	Add environment variable
container_no_sftp_channel	No SFTP channel available. Please configure an SFTP connection in the "Connections & Sync" page first.
container_select_ssh_channel	Select SSH Channel
container_ssh_channel	SSH Channel
container_remote_workspace_path	Remote Workspace Path
container_announcement_title	FAQ
container_announcement_got_it	Got it
container_download_image	Download Images
container_download_architecture	Architecture: %1${'$'}s
container_download_current_source	Source: %1${'$'}s
container_download_current_source_title	Choose download source
container_download_source_unavailable	Not available on this mirror
container_download_start	Download
container_download_redownload	Redownload
container_download_cancel	Cancel download
container_download_import	Import
container_download_imported_toast	Imported to the container list; it will be extracted on first use in Terminal
container_download_failed	Download failed: %1${'$'}s
container_download_list_empty	No images available
container_download_done	Downloaded
container_download_newer_available	Update available
container_download_checksum_failed	Checksum mismatch — the file may be truncated or tampered with. Please retry.
container_download_eol_soon	Support ending soon
container_download_eol_ended	No longer supported
container_download_eol_extended	In %1${'$'}s extended support
container_download_eol_extended_generic	In extended support
container_download_eol_support_ends	Standard support ended: %1${'$'}s
container_download_eol_fully_ends	Fully end-of-life: %1${'$'}s
container_download_size	Size: %1${'$'}s
container_download_delete_title	Delete downloaded image
container_download_delete_confirm	Delete the downloaded file for "%1${'$'}s"? Imported containers will not be able to extract again.
credential_edit	Edit Credential
credential_host	Remote Host
credential_toast_saved	Credential saved
credential_toast_save_failed	Save failed: %1${'$'}s
credential_toast_deleted	Credential deleted
credential_toast_identity_saved	Identity and repository URL saved
credential_empty_hint	No credentials yet, tap + to add
credential_empty_title	No Git Credentials
credential_empty_desc	Tap + in the top right to sign in with GitHub or add an access token. AiCode then authenticates pushes and pulls automatically
credential_add_sheet_title	Add Credential
credential_add_sheet_subtitle	Choose quick authorization or configure access credentials manually
credential_add_manual	Add manually
credential_add_manual_desc	Fill in remote host, username and access token
credential_add_github_title	GitHub Quick Sign-in
credential_add_github_desc	One-tap sign-in with GitHub account, no manual token required
credential_add_manual_title	Configure Manually
credential_add_manual_subtitle	For GitLab, Gitee, self-hosted servers or personal access tokens
credential_add_recommend	Recommended
credential_quick_login	Quick login
credential_quick_login_desc	Sign in via GitHub device code
credential_quick_login_title	Quick login
credential_quick_login_github_desc	Sign in with your GitHub account
credential_device_title	GitHub Sign-in
credential_device_subtitle	Securely authorize access permissions via GitHub device flow
credential_device_requesting	Requesting device code…
credential_device_instruction	Open the address below in a browser and enter the device code to authorize
credential_device_card_label	Verification Code
credential_device_tap_to_copy	Tap to copy
credential_device_code_copied	Device code copied
credential_device_code_copied_opening	Device code copied, opening browser…
credential_device_authorize_and_open	Copy & Authorize
credential_device_copy_code	Copy code only
credential_device_open_browser	Open browser
credential_device_step_1	1. Copy the 8-character verification code above
credential_device_step_2	2. Paste and confirm authorization on the GitHub page
credential_device_waiting	Waiting for browser authorization…
credential_device_waiting_desc	Credentials will be bound and saved automatically once authorized
credential_device_success_title	Authorization Successful
credential_device_success	Signed in as %1${'$'}s and credential saved
credential_device_install_required	Signed in as %1${'$'}s. You still need to install AiCode on your repositories before pushing/pulling code.
credential_device_install_now	Install
credential_device_install_later	Later
credential_device_failed_title	Authorization Incomplete
credential_device_failed	Sign-in failed: %1${'$'}s
credential_device_expired	Device code expired, please retry
credential_device_denied	Authorization was denied
credential_device_flow_disabled	Device flow is not enabled for this app, please enable it in the GitHub App settings
credential_device_bad_client	Invalid client ID, please check the app configuration
credential_device_unknown_error	Unknown error
credential_prompt_title	Login credentials needed for %1${'$'}s
credential_remote_host	Remote Host
credential_prompt_desc	Once filled, it will be saved and retried automatically. The credential is also saved to git inside the container, so terminal and AI can use it directly for bare git commands.
credential_save_and_retry	Save and Retry
diff_no_changes	No differences
diff_binary_file	Binary file, text diff not supported
diff_file_too_large	File too large, diff calculation skipped
diff_file_too_large_desc	Files over 2000 lines or with extremely long lines are not diffed line-by-line, to avoid rendering and memory pressure.
git_credentials_and_identity	Credentials & Identity
git_title	Git
git_refresh	Refresh
git_tab_status	Status
git_tab_branches	Branches
git_tab_commits	Commits
git_computing_diff	Computing diff...
git_status_clean	Working tree clean, no changes
git_commit_needs_identity	No commit identity configured. Set your name and email in “Credentials & Identity”.
git_staged_count	Staged (%1${'$'}s)
git_unstage	Unstage
git_modified_count	Modified (%1${'$'}s)
git_stage	Stage
git_untracked_count	Untracked (%1${'$'}s)
git_clean	Working tree clean
git_has_changes	Working tree has changes
git_no_branch	(no branch)
git_staged_label	Staged
git_modified_label	Modified
git_untracked_label	Untracked
git_commit_changes	Commit Changes
git_no_identity	No identity configured, cannot commit. Please fill in user.name / user.email in the "Credentials" page.
git_stage_all	Stage All
git_pull	Pull
git_push	Push
git_loading_branches	Loading branch list…
git_no_branches	No branches
git_no_checked_out_branch	No branch checked out
git_switch_branch	Switch Branch
git_detached_head_warning	Switching to tag %1${'$'}s will enter detached HEAD state — not on any branch, new commits won't belong to any branch. Continue?
git_create_tracking_branch	Create a local tracking branch from remote branch %1${'$'}s and switch?
git_switch_confirm	Switch to %1${'$'}s?
git_new_branch	New Branch
git_branch_name	Branch name
git_base_branch	Base branch
git_create_and_switch	Create and Switch
git_delete_remote_branch	Delete Remote Branch
git_delete_branch	Delete Branch
git_delete_remote_branch_confirm	Delete remote branch %1${'$'}s? This action cannot be undone.
git_delete_local_branch_confirm	Delete local branch %1${'$'}s?
git_rename_branch	Rename Branch
git_rename_to	Rename %1${'$'}s to:
git_new_branch_name	New branch name
git_create_tag_desc	Create lightweight tag from current HEAD (%1${'$'}s)
git_tag_name	Tag name
git_delete_tag	Delete Tag
git_delete_tag_confirm	Delete local tag %1${'$'}s?
git_checked_out	Checked out
git_current_branch	Current branch
git_remote	Remote
git_tags	Tags
git_new	New
git_no_commits	No commits yet
git_commit_count	Commits (%1${'$'}s)
git_credentials_count	Credentials (%1${'$'}d)
git_load_more_commits	Pull up to load more commits
git_no_more_commits	No more commits
git_latest_commit	Latest commit
git_recent_commit	Recent commit
git_expanded	Expanded
git_latest_date	Latest date
git_files_changed	%1${'$'}s files changed
git_no_files_changed	No files changed in this commit
git_loading_files	Loading changed files...
git_not_a_repo	Current workspace is not a Git repository yet
git_init_desc_primary	Initialize a new repository here to start version control
git_init_desc_secondary	Or pick an existing repository from a subdirectory
git_init_repo	Initialize New Repository
git_clone_repo	Clone Remote Repository
git_clone_dialog_title	Clone Git Repository
git_clone_desc	Clone a remote repository into the current workspace root (directory must be empty). For private repos, configure credentials via the key icon first.
git_clone_url_label	Repository URL
git_clone_url_hint	https://github.com/user/repo.git
git_cloning_hint	Cloning repository, please wait…
git_commit_dialog_title	Commit Changes
git_commit_message	Commit message
git_tracking_branch	Tracking %1${'$'}s
git_no_upstream	No upstream set
git_detached_head_label	detached HEAD: not on any branch
git_ahead_count	%1${'$'}s to push
git_behind_count	%1${'$'}s to pull
git_copy_short_hash	Copy short hash
git_copy_full_hash	Copy full hash
git_branch_synced	Synced
git_merge_commit	Merge commit
git_unstage_all_confirm	All files will be unstaged, but their contents will not be deleted. Continue?
git_pull_dirty_confirm	The working tree has uncommitted changes. Pulling may cause conflicts. Commit or stage them first if possible. Continue pulling?
git_pull_continue	Continue Pull
git_switch_repo	Switch repository
git_pick_existing_repo	Pick Existing Repository
git_repo_selector_title	Select Repository
git_repo_not_repo	Not a repo
git_pick_repo_empty	No directories available in the current workspace
git_push_master_confirm_title	Branch Naming Suggestion
git_push_master_confirm_desc	Current local branch is "master". GitHub and GitLab recommend using "main" as the default branch. Would you like to rename it to "main" before pushing?
git_push_rename_to_main	Rename to main & Push
git_push_keep_master	Keep master & Push
git_toast_load_branches_failed	Failed to load branches: %1${'$'}s
git_toast_refresh_failed	Refresh failed: %1${'$'}s
git_toast_action_refresh_failed	%1${'$'}s (refresh failed)
git_toast_no_remote_pull	No remote repository configured, cannot pull
git_toast_no_remote_push	No remote repository configured, cannot push
git_toast_load_more_failed	Failed to load more: %1${'$'}s
git_toast_action_success	%1${'$'}s successful
git_toast_action_failed	%1${'$'}s failed: %2${'$'}s
git_toast_checkout_success	Switched to %1${'$'}s
git_toast_checkout_failed	Switch failed: %1${'$'}s
git_toast_not_repo	This directory is not a Git repository
git_toast_switch_repo_failed	Switch repository failed: %1${'$'}s
git_toast_diff_failed	Failed to load diff
git_toast_path_copied	Path copied
git_action_stage_all	Stage all
git_action_unstage_all	Unstage all
git_action_commit	Commit
git_action_init	Initialize
git_action_clone	Clone
git_action_create_branch	Create branch
git_action_delete_branch	Delete branch
git_action_delete_remote_branch	Delete remote branch
git_action_rename_branch	Rename branch
git_action_create_tag	Create tag
git_action_delete_tag	Delete tag
git_action_view_diff	View diff
git_action_revert	Revert changes
git_action_restore_file	Restore file
git_action_revert_all	Revert all
git_action_delete_file	Delete file
git_action_delete_dir	Delete folder
git_action_copy_path	Copy path
git_revert_confirm_worktree	Restore %1${'$'}s to its last staged or committed content? Unstaged changes will be lost and cannot be recovered.
git_revert_confirm_head	Restore %1${'$'}s to its last committed content? Both staged and unstaged changes to this file will be lost and cannot be recovered.
git_restore_file_confirm	Restore the deleted %1${'$'}s? The file will be brought back from the last commit.
git_revert_all_confirm	Discard all unstaged changes (%1${'$'}d files) and restore them to the last staged or committed content? Staged content is unaffected, but discarded changes cannot be recovered.
git_delete_untracked_confirm	Delete %1${'$'}s? It has never been committed, so Git cannot recover it after deletion.
git_action_merge	Merge Branch
git_merge_into	Merge into %1${'$'}s
git_merge_confirm	Merge branch %1${'$'}s into current branch (%2${'$'}s)?
git_merge_no_ff	Create a merge commit (--no-ff)
git_tab_stash	Stash
git_stash_title	Stashes
git_action_stash	Stash Changes
git_stash_message_hint	Stash message (optional)
git_stash_include_untracked	Include untracked files (-u)
git_stash_empty	No stashes found
git_stash_pop	Pop
git_stash_apply	Apply
git_stash_drop	Drop
git_stash_drop_confirm	Drop stash %1${'$'}s? This action cannot be undone.
git_stash_clear	Clear All
git_stash_clear_confirm	Clear all stashes? This action cannot be undone.
git_stash_count	Stashes (%1${'$'}d)
git_conflicted_count	Conflicts (%1${'$'}s)
git_merge_pending_title	Merge Awaiting Commit
git_merge_pending_hint	There are no unresolved conflicts. Review and stage the changes you want to keep, then commit to complete the merge.
git_action_abort_merge	Abort Merge
git_abort_merge_confirm	Abort the current merge? Unresolved changes will be discarded and the working tree will be restored.
git_commit_ai_generate	AI Generate
git_commit_generating	Generating…
git_commit_amend	Amend last commit (--amend)
git_toast_no_diff_for_commit	No changes found to generate commit message
git_toast_generate_commit_failed	Failed to generate commit message, please check model or network
git_open_in_editor	Open in Editor
git_create_branch_from_commit	Create Branch from Commit
git_create_tag_at_commit	Create Tag at Commit
git_reset_to_commit	Reset to this Commit
git_reset_soft	Soft (Keep working tree and staged)
git_reset_mixed	Mixed (Keep working tree, unstage)
git_reset_confirm	Reset current branch to commit %1${'$'}s?
git_search_commits_hint	Search commit message or author…
git_add_to_gitignore	Add to .gitignore
git_ignore_file	Ignore this file (%1${'$'}s)
git_ignore_dir	Ignore directory (%1${'$'}s/)
git_ignore_extension	Ignore same extension (*.%1${'$'}s)
git_toast_added_to_gitignore	Added to .gitignore
git_tab_identity	Identity
git_tab_credentials	Credentials
git_identity_title	Commit Identity
git_identity_desc	Used as author for every commit. Applies globally in the container, shared across all workspaces.
git_repo_url_title	Repository URL
git_remote_url	remote.origin.url
mcp_add	Add MCP
mcp_edit	Edit MCP
mcp_view_logs	View this MCP's logs
mcp_refresh_tools	Refresh Tools
mcp_scope_label	Scope
mcp_scope_global	Global
mcp_scope_project	Project
mcp_basic_settings	Basic Settings
mcp_enabled	Enabled
mcp_name_hint	e.g. fetch, github, gitlab
mcp_name_invalid	Name can only contain letters, digits, underscores, and hyphens
mcp_transport_type	Transport Type
mcp_remote_http	Remote HTTP
mcp_local_stdio	Local stdio
mcp_save_first_hint	Please save and connect to the server first to view the tool list
mcp_no_tools_hint	No tools detected, tap the refresh button in the top right to retry
mcp_no_description	No description
mcp_server_url	Server URL
mcp_server_url_hint	e.g. https://api.example.com/sse
mcp_custom_headers	Custom Headers
mcp_no_custom_headers	No custom headers configured
mcp_header_name	Header Name
mcp_header_name_hint	e.g. Authorization
mcp_header_value	Header Value
mcp_header_value_hint	e.g. Bearer xxxxxx
mcp_add_header	Add Header
mcp_empty	No MCP servers configured
mcp_empty_hint	Tap + in the top right to add a remote HTTP or local stdio server
mcp_disabled	Disabled
mcp_not_connected	Not connected
mcp_connected	Connected
mcp_connecting	Connecting…
mcp_connection_failed	Connection failed
mcp_needs_auth	Sign-in required
mcp_auth_enable	OAuth authentication
mcp_auth_desc	For remote MCP servers that require browser sign-in
mcp_auth_client_id	client_id (optional)
mcp_auth_client_id_hint	Pre-registered client ID; leave blank to try dynamic registration
mcp_auth_server	Authorization server (optional)
mcp_auth_server_hint	Override the auto-discovered authorization server
mcp_auth_advanced	Advanced OAuth options
mcp_auth_login	Sign in / Re-authorize
mcp_auth_reauthorize	Re-authorize
mcp_auth_logout	Sign out
mcp_auth_cancel	Cancel authorization
mcp_auth_in_progress	Waiting for browser authorization…
mcp_auth_in_progress_hint	Opened sign-in page in browser; will reconnect automatically once completed
mcp_auth_save_first	Save the server configuration first to sign in here
mcp_auth_failed	Authorization failed: %1${'$'}s
mcp_auth_status_label	Authorization status
mcp_auth_status_authorized	Signed in
mcp_auth_status_unauthorized	Not signed in
mcp_auth_status_authorizing	Authorizing
mcp_auth_success	Authorized and connected
mcp_search_placeholder	Search MCP servers…
mcp_search_empty	No matching MCP servers found
mcp_tools_search	Search tool name or description…
mcp_tools_search_empty	No matching tools found
mcp_tools_summary	%1${'$'}d / %2${'$'}d tools enabled
mcp_tools_enable_all	Enable all
mcp_tools_disable_all	Disable all
mcp_add_sheet_title	Add MCP server
mcp_add_manual	Fill in manually
mcp_add_manual_desc	Enter URL, headers, or launch command one by one
mcp_add_from_json	Import from JSON
mcp_add_from_json_desc	Paste a standard mcpServers config to import in bulk
mcp_json_import_title	Import from JSON
mcp_json_import_hint	Paste an mcpServers config, e.g. {"mcpServers":{"demo":{"url":"https://..."}}}
mcp_json_import_action	Import
mcp_json_import_empty	No MCP server was parsed
mcp_type_stdio	stdio
mcp_tools_count	%1${'$'}d tools
mcp_details	Details
prompts_title	Prompts
prompts_sort_long_press	Long-press to reorder
prompts_empty	No fragments available
prompts_source_project	Project
prompts_source_global	Global
prompts_source_local	Local
prompts_source_builtin	Built-in
prompts_field_basic	Basic
prompts_field_number	Number
prompts_field_number_placeholder	Two digits, e.g. 35
prompts_field_title	Name
prompts_field_title_placeholder	e.g. My safety rules
prompts_field_summary	Summary (optional)
prompts_field_summary_placeholder	Brief description; not included in the prompt
prompts_field_content	Fragment content
prompts_field_content_placeholder	What the model should follow
prompts_editor_new	New prompt
prompts_editor_edit	Edit prompt
prompts_add_prompt	Add fragment
prompts_reset_custom	Reset custom prompts
prompts_reset_confirm	Deletes every custom prompt fragment in this project and the global layer; each number falls back to the built-in prompt. This cannot be undone.
prompts_help	Help
prompts_help_body	Prompt fragments inject in number order: builtin → global → project (closest wins). Tap to preview, long-press to reorder, swipe to delete custom fragments, tick to batch select.
window_minimize	Minimize window
window_maximize	Maximize / restore
window_close	Close window
prompts_disable_builtin	Disable all built-in prompts
prompts_disable_builtin_hint	Keep only your own fragments; you must supply every rule yourself
skills_empty	No skills installed
skills_empty_hint	Tap + in the top bar to create or import one.
skills_no_project_skills	No skills for the current project
skills_no_global_skills	No global skills
skills_enable	Enable
skills_summary	Summary
skills_delete_confirm_title	Delete Skill
skills_delete_confirm_message	This will delete the skill directory of "%1${'$'}s". This action cannot be undone. Delete?
skills_description	Description
skills_instructions	Instructions
skills_add	Add skill
skills_edit	Edit skill
skills_editor_new	New skill
skills_editor_edit	Edit skill
skills_editor_basic	Basics
skills_editor_scope	Scope
skills_scope_global	Global
skills_scope_project	This project
skills_name_hint	Lowercase is recommended, e.g. pdf-report
skills_description_hint	When to use it; the AI decides whether to load it by this text
skills_instructions_hint	Describe the standard workflow and caveats for this kind of task; Markdown supported
skills_editor_error_invalid_name	Invalid name: it cannot be empty or contain path separators and other special characters
skills_editor_error_empty_instructions	The instructions cannot be empty
skills_editor_error_name_conflict	A skill with this name already exists in this scope
skills_editor_error_io	Failed to write the skill file, please try again
skills_add_sheet_title	Add skill
skills_add_manual	Create manually
skills_add_manual_desc	Fill in the name and content to create one from scratch
skills_add_from_file	Import from file
skills_add_from_file_desc	Pick a .md file with the instruction body
skills_add_from_zip	Import from archive
skills_add_from_zip_desc	Pick a .zip; may contain multiple skills
skills_import_running	Importing…
skills_import_result_title	Import result
skills_import_success	Imported %1${'$'}d skill(s)
skills_import_partial	Imported %1${'$'}d, skipped %2${'$'}d
skills_import_failure_line	%1${'$'}s: %2${'$'}s
skills_import_error_invalid_name	invalid name
skills_import_error_name_conflict	a skill with the same name already exists
skills_import_error_empty_content	empty content
skills_import_error_no_skill	no skill found in the archive
skills_import_error_invalid_archive	cannot read the archive; make sure it is a valid .zip
skills_import_error_unsupported_file	unsupported file type
skills_import_error_io	failed to write the skill file
settings_subagents	Subagents
subagents_empty	No custom subagents yet
subagents_empty_hint	Tap + in the top bar to create one, or drop an agent's .md definition into ~/.aicode/agents/
subagents_no_project	No subagents in this project
subagents_no_global	No global subagents
subagents_delete_confirm_title	Delete subagent
subagents_delete_confirm_message	This deletes the definition file of "%1${'$'}s" and cannot be undone. Delete it?
subagent_model	Model
subagent_tools	Tools
subagent_inject	Injected prompts
subagent_prompt	Agent prompt
subagent_source_file	Definition file
subagent_inherit_parent	Inherit from parent session
subagent_all_tools	All tools except task
subagent_tools_allow	Allowed only: %1${'$'}s
subagent_tools_deny	Denied: %1${'$'}s
subagent_inject_none	No extra prompt injected
subagent_add	New subagent
subagent_edit	Edit subagent
subagent_enable	Enable this subagent
subagent_enable_hint	When off, the AI can no longer dispatch it; running subagents are unaffected
subagent_editor_new	New subagent
subagent_editor_edit	Edit subagent
subagent_editor_basic	Basics
subagent_editor_description	Description
subagent_editor_scope	Scope
subagent_scope_global	Global
subagent_scope_project	This project
subagent_name_hint	Lowercase is recommended, e.g. researcher
subagent_description_hint	When to dispatch it; the main session picks agents by this text
subagent_editor_model_group	Model
subagent_editor_effort	Reasoning effort
subagent_editor_mode	Default mode
subagent_editor_tools_allow	Allowed tools
subagent_editor_tools_allow_hint	Leave empty to allow every tool except task
subagent_editor_tools_deny	Denied tools
subagent_editor_tools_deny_hint	Takes precedence over the allow list
subagent_editor_tools_search	Search tools
subagent_editor_selected_count	%1${'$'}d selected
subagent_editor_none	None
subagent_editor_clear	Clear
subagent_prompt_hint	Describe its role, how it should work and what to deliver
subagent_no_models	No models available. Add and configure a model under AI Vendors first.
subagent_editor_error_invalid_name	Invalid name: it cannot be empty or contain path separators and other special characters
subagent_editor_error_empty_prompt	The agent prompt cannot be empty
subagent_editor_error_name_conflict	A subagent with this name already exists in this scope
subagent_editor_error_io	Failed to write the definition file, please try again
subagent_inject_base	Subagent baseline
subagent_inject_base_hint	Tool usage and path conventions, lightweight
subagent_inject_main_rules	Full common rules
subagent_inject_main_rules_hint	Pick either this or the subagent baseline; enabling both injects overlapping rules
subagent_inject_skills	Skill list
subagent_inject_skills_hint	Lets it load and run project skills
subagent_inject_memory	Memory summaries
subagent_inject_memory_hint	Injects global and project long-term memories
subagent_inject_project_rules	Project rules
subagent_inject_project_rules_hint	Injects AGENTS.md or CLAUDE.md
mcp_command	Command
mcp_command_hint	e.g. npx
mcp_args	Arguments
mcp_no_args	No extra arguments
mcp_arg_value	Argument Value
mcp_arg_hint	e.g. -y or @modelcontextprotocol/server-xxx
mcp_add_arg	Add Argument
mcp_env_vars	Environment Variables
mcp_no_env_vars	No custom environment variables
mcp_env_name	Variable Name
mcp_env_name_hint	e.g. API_KEY
mcp_env_value	Variable Value
mcp_env_value_hint	e.g. sk-xxxx
mcp_add_env_var	Add Environment Variable
perm_rules_short	Choosing "Always Allow" in chat creates remembered rules matched by command or path
perm_current_project	Current project: %1${'$'}s
perm_current_project_none	Current project (not selected)
perm_no_project_rules	No rules for this project
perm_global	Global
perm_no_global_rules	No global rules
perm_entire_tool	Entire tool
perm_deny	Deny
perm_promote_to_global	Promote to global
perm_safety_group	Safety
perm_disable_safety_interception	Disable safety interception
perm_disable_safety_interception_desc	When on, AUTO mode stops blocking catastrophic deletes and Shizuku prompts
perm_safety_confirm_title	Disable safety interception?
perm_safety_confirm_message	Once disabled, AUTO mode no longer blocks catastrophic deletes and no longer prompts for the Shizuku tool. Proceed with caution.
perm_safety_confirm_action	Confirm (%1${'$'}d)
perm_safety_confirm_action_ready	Confirm
provider_section_basic	General
provider_section_type	Upstream Format
provider_section_options	Options
provider_show_api_key	Show API Key
provider_hide_api_key	Hide API Key
provider_api_key	API Key
provider_base_url	Base URL
provider_api_key_required	Please enter an API Key first
provider_fetch_missing_fields	Response is missing the list field
provider_fetch_missing_fields_detail	Response missing list field (models or data): %1${'$'}s
provider_fetch_success	Fetched %1${'$'}d models · %2${'$'}dms
provider_fetch_http_error	HTTP %1${'$'}d · %2${'$'}dms
provider_test_success	Connected · %1${'$'}dms
common_error	Error
common_select	Select
provider_model_context_total	Total DesktopContext Window (tokens)
provider_model_window_invalid	Enter a valid positive integer, or leave blank to use the automatically detected value
provider_model_context_input	Input Window (tokens)
provider_model_context_output	Output Window (tokens)
provider_new	New Vendor
provider_add	Add Vendor
provider_edit	Edit Vendor
provider_add_model	Add Model
provider_config	Config
provider_full_url	Full URL
provider_full_url_desc	Base URL is the full request address, no default path appended
provider_headers_title	Custom Headers
provider_headers_desc	Completely override the same-name default header for this vendor
provider_headers_name	Header Name
provider_headers_name_hint	e.g. Authorization
provider_headers_value	Value
provider_headers_value_hint	e.g. Bearer sk-xxx
provider_headers_add	Add header
provider_headers_remove	Remove header
provider_headers_manage	Manage
provider_headers_empty	No headers yet; add one below
provider_headers_count	%1${'$'}d headers configured
provider_response_api	Response API (new)
provider_interactions_api	Interactions API (new)
provider_models_count	Models (%1${'$'}s)
provider_models_count_tag	%1${'$'}d models
provider_fetch_models	Fetch Models
provider_add_all_models	Add All
provider_add_all_models_title	Add All Models
provider_add_all_models_confirm	Add all %1${'$'}d fetched models to this provider?
provider_models_select	Select
provider_models_select_all	Select All
provider_models_deselect_all	Deselect All
provider_models_selected_count	%1${'$'}d selected
provider_models_batch_test	Test
provider_models_batch_delete	Delete
provider_models_batch_delete_title	Delete Models
provider_models_batch_delete_confirm	Are you sure you want to delete the selected %1${'$'}d models?
common_select_mode	Select
common_select_all	Select All
common_deselect_all	Deselect All
common_selected_count	%1${'$'}d selected
common_batch_delete	Delete
common_batch_enable	Enable
common_batch_disable	Disable
common_batch_reconnect	Reconnect
common_batch_deleting	Deleting…
providers_batch_delete_title	Delete Providers
providers_batch_delete_confirm	Delete the selected %1${'$'}d providers? This cannot be undone.
mcp_batch_delete_title	Delete MCP Servers
mcp_batch_delete_confirm	Delete the selected %1${'$'}d MCP servers?
skills_batch_delete_title	Delete Skills
skills_batch_delete_confirm	Delete the selected %1${'$'}d skill directories? This cannot be undone.
subagents_batch_delete_title	Delete Subagents
subagents_batch_delete_confirm	Delete the selected %1${'$'}d subagent definition files? This cannot be undone.
prompts_batch_delete_title	Delete Prompts
prompts_batch_delete_confirm	Delete the selected %1${'$'}d custom prompt fragments? Their numbers will fall back to the next source.
container_download_batch_delete_title	Delete Downloaded Images
container_download_batch_delete_confirm	Delete the selected %1${'$'}d image files? Imported containers will no longer be able to unpack them.
provider_model_name	Model Name
provider_model_already_added	This model has already been added
provider_edit_model	Edit Model
provider_model_section_price	Pricing
provider_model_price_input	Input Price (USD/1M tokens)
provider_model_price_output	Output Price (USD/1M tokens)
provider_model_price_cache_read	Cache Read Price (USD/1M tokens)
provider_model_price_cache_write	Cache Write Price (USD/1M tokens)
provider_model_cap_vision	Image Input
provider_model_cap_image_output	Image Output
provider_model_capabilities	Capabilities
provider_model_capability_tools	Tool Calling
provider_model_capability_reasoning	Reasoning
provider_filter_models_hint	Filter models by name
provider_fetch_failed	Fetch failed: %1${'$'}s
provider_no_matching_models	No matching models
provider_models_aligned_removed	Removed %1${'$'}d model(s) no longer offered by the vendor
provider_please_wait	Please wait…
provider_test	Test
provider_brand_doubao	Doubao
provider_brand_zhipu	Zhipu
provider_brand_tongyi_qianwen	Tongyi Qianwen
provider_brand_hunyuan	Hunyuan
provider_brand_siliconflow	SiliconFlow
provider_model_icon	Model icon
providers_empty	No vendors yet, tap + to add one
provider_sort_long_press	Long press to reorder
log_level	Log Level
log_level_desc	Logs below this level will be ignored. Use VERBOSE during development (log all); NONE to disable logging.
log_file_location	Log files are located at Android/data//files/logs/.
provider_no_models	No Models
provider_no_models_hint	Tap the button in the top-right to add a model
remote_edit_connection	Edit Connection Channel
remote_add_connection	Add Connection Channel
remote_connection_name_hint	Connection name
remote_connection_sort_long_press	Long press to reorder
remote_host_address	Host
remote_port	Port
remote_password	Password
remote_auth_password	Password
remote_auth_key	Key
remote_login_key	Login key
remote_select_key	Select key
remote_no_keys_hint	No keys yet, add one in the SSH Host Keys page
remote_key_passphrase	Passphrase (optional)
remote_toggle_password	Toggle password visibility
remote_test_connection	Test Connection
remote_edit_workspace	Edit Mount
remote_add_workspace	Add Mount
remote_select_channel	Select Remote Channel
remote_link_channel	Link Remote Connection Channel
remote_target_dir	Remote Target Directory
remote_browse_dir	Browse Directory
remote_select_local_workspace	Select Local Workspace
remote_mount_already_exists	This mount already exists: same channel, remote directory and workspace combination
remote_map_to_local	Map to Local Workspace
remote_no_local_workspace	No local workspaces, please create one in the sidebar first.
remote_auto_connect_on_start	Auto-connect on app start
remote_auto_connect_and_sync	Auto-connect and sync this workspace on app start
remote_select_remote_dir	Select Remote Directory
remote_current_path	Current path: %1${'$'}s
remote_load_failed	Load failed: %1${'$'}s
remote_parent_dir	.. (parent)
remote_confirm_select	Confirm Selection
ftp_usage_title	Usage Guide
ftp_usage_item_1	Phone and computer on the same WiFi
ftp_usage_item_2	Enter the address below in the computer's file explorer
ftp_usage_item_3	Manage projects in the phone's workspace
ftp_running	Running: %1${'$'}s
ftp_not_running	Service not running
ftp_error	Error: %1${'$'}s
ftp_config_title	Service Configuration
ftp_listen_port	Listen Port
ftp_login_username	Login Username
ftp_login_password	Login Password
ftp_allow_anonymous	Allow Anonymous
ftp_anonymous_desc	Connect without username and password
ftp_anonymous_confirm_title	Allow anonymous access?
ftp_anonymous_confirm_message	Once enabled, any device on the same LAN can read and write your workspace without credentials.
ftp_password_required	Set a username and password first
ftp_reserved_username	anonymous is reserved for anonymous login. Use a different username.
ftp_user_cache_cleanup_failed	Could not clear old FTP login data. The server was not started. Try again.
ftp_auto_start	Auto-start on app launch
ftp_auto_start_desc	Automatically start FTP service in background on app launch
ftp_config_saved	Configuration saved
ftp_save_config	Save Configuration
ftp_copy_address	Copy address
ftp_address_copied	Address copied
remote_workspace_title	Connections & Sync
remote_tab_connections	Connections
remote_tab_mounts	Mounted Directories
remote_tab_ftp	Built-in FTP
remote_tab_sync	Sync Settings
remote_no_connections	No connections yet
remote_no_connections_desc	Add an SFTP / FTP connection
remote_no_workspaces	No mounted directories yet
remote_no_workspaces_desc	Local changes auto-sync to the remote; remote files can be downloaded manually
remote_hint_title	Hint
remote_add_channel_first	Please add a remote channel in "Connections" first.
remote_go_add	Go Add
remote_upload_failed	Upload failed: %1${'$'}s
remote_upload_success	Upload complete
remote_upload_full_failed	Full upload failed: %1${'$'}s
remote_upload_full_success	Full upload complete
remote_clear_index_done	Sync record cleared
remote_download_all_failed	Full download failed: %1${'$'}s
remote_download_all_success	Full download complete
remote_connect_success	Connected
remote_connect_failed	Connection failed: %1${'$'}s
remote_unknown_error	Unknown error
ssh_host_key_settings_title	SSH Host Keys
ssh_host_key_tab_keys	Keys
ssh_host_key_tab_fingerprints	Fingerprints
ssh_login_key_add	Add key
ssh_login_key_empty	No saved login keys
ssh_login_key_fingerprint_label	Public key fingerprint
ssh_login_key_encrypted	Encrypted (passphrase required)
ssh_login_key_add_method_title	Add login key
ssh_login_key_add_from_file	Import from file
ssh_login_key_add_from_file_desc	Pick a private key file on this device
ssh_login_key_add_from_paste	Paste key
ssh_login_key_add_from_paste_desc	Paste the private key content directly
ssh_login_key_paste_title	Paste key
ssh_login_key_paste_name_placeholder	Leave blank to name automatically
ssh_login_key_paste_content_placeholder	-----BEGIN OPENSSH PRIVATE KEY-----
ssh_login_key_paste_invalid	Invalid private key content, please check and retry
ssh_login_key_edit_title	Edit key
ssh_login_key_name_label	Name
ssh_login_key_content_label	Private key
ssh_login_key_passphrase_placeholder	Leave blank if the key is not encrypted
ssh_login_key_toggle_content	Show or hide the private key
ssh_host_key_confirm_title	Confirm SSH Host Key
ssh_host_key_changed_title	SSH Host Key Changed
ssh_host_key_trust	Trust and connect
ssh_host_key_reject	Reject
ssh_host_key_settings_desc	Confirmed host fingerprints help prevent man-in-the-middle attacks. Verify a server after reinstalling it or rotating its key before confirming again.
ssh_host_key_empty	No saved host fingerprints
ssh_host_key_fingerprint_value	Fingerprint: %1${'$'}s
ssh_host_key_remove	Remove fingerprint
ssh_host_key_detail	Saved server host fingerprint:\n%1${'$'}s
sync_save_settings	Save Settings
sync_settings_title	Sync Settings
sync_saved	Sync settings saved
sync_follow_gitignore	Follow .gitignore rules
sync_gitignore_desc	Automatically reads the project root .gitignore and ignores its contents.
sync_max_batch_size	Max Sync Batch Size
sync_batch_size_desc	Operations exceeding this limit are queued and processed in batches to avoid disconnects.
sync_max_batch_count	Max Count
sync_path_mapping	%1${'$'}s → %2${'$'}s
status_reconnecting	Reconnecting
status_connected	Connected
status_disconnected	Disconnected
status_connection_failed	Connection Failed
sync_disconnect	Disconnect
sync_upload_changes	Upload Changes
sync_upload_full	Force Full Upload
sync_clear_index	Clear Sync Record
sync_download_all	Download All
sync_connect_and_sync	Connect and Sync
terminal_title	Terminal
terminal_prepare_error_unknown	Unknown error
terminal_start_failed	Cannot start terminal:\n%1${'$'}s
terminal_retry	Retry
terminal_no_open_tabs	No open terminal tabs
terminal_close_tab	Close Tab
terminal_cleaning_old_container	Cleaning up old container data…\n%1${'$'}s items removed
terminal_extracting_env	Extracting environment…\n%1${'$'}s files processed
terminal_installing_packages	Installing python3 / git / pip / node / npm…\n%1${'$'}s
terminal_preparing_env_failed	Preparing container environment…\n%1${'$'}s
terminal_preparing_env_first_run	Preparing container environment…\nFirst run will extract the container, please wait
terminal_settings_title	Terminal Settings
terminal_theme_title	Color Theme
terminal_font_size_title	Font Size
terminal_cursor_style_title	Cursor Style
terminal_cursor_block	Block
terminal_cursor_underline	Underline
terminal_cursor_bar	Bar
terminal_font_title	Terminal Font
terminal_font_import	Import
terminal_font_default	System Monospace
terminal_font_builtin	JetBrains Mono
terminal_font_delete	Delete this font
terminal_font_import_failed	Import failed. Please pick a valid ttf / otf / ttc font file
terminal_preview_text	Terminal Preview
terminal_theme_follow_app	Follow App
terminal_theme_github_light	GitHub Light
terminal_theme_termius_dark	Termius Dark
terminal_theme_dracula	Dracula
terminal_theme_one_dark	One Dark
terminal_theme_monokai	Monokai
terminal_theme_github_dark	GitHub Dark
todo_completed	Completed
todo_in_progress	In Progress
todo_dashboard_title	Tasks
todo_dashboard_progress	%1${'$'}d/%2${'$'}d done
todo_dashboard_all_done	All done
tool_instruction	Instruction
tool_result	Result
tool_expand_remaining	Expand remaining %1${'$'}s lines
tool_changed_files_summary	+%1${'$'}d −%2${'$'}d · %3${'$'}d files
vision_no_models	No models available. Please add and configure a model in "AI Vendors" before selecting a vision model.
compaction_no_models	No models available. Please add and configure a model in "AI Vendors" before selecting a dedicated compaction model.
title_no_models	No models available. Please add and configure a model in "AI Vendors" before selecting a dedicated title model.
image_gen_no_models	No models available. Please add and configure a model in "AI Vendors" (must support image output, e.g. gpt-image-1 / dall-e-3) before selecting an image generation model.
web_search_title	Web Search
web_search_results_count	%1${'$'}s results found
web_search_warning	Warning: %1${'$'}s
workspace_select	Select Workspace
workspace_switch	Switch Workspace
workspace_switch_confirm	Switching workspace will stop all running AI sessions and close terminal tabs in the current workspace. Continue?
workspace_confirm	Confirm
workspace_open	Open Workspace
workspace_new_internal	New Internal Workspace
workspace_empty_hint	No workspaces, tap %1${'$'}s to create one
workspace_delete	Delete Workspace
workspace_delete_confirm	Delete "%1${'$'}s"? All files in this directory and this workspace's chats will be deleted and cannot be undone.
workspace_new_workspace	New Workspace
workspace_name_hint	e.g. my-project
workspace_name_exists	A workspace with this name already exists
workspace_remote_root_create_failed	Failed to create the remote workspace directory. Check the path and server permissions.
workspace_remote_default_create_failed	Failed to create the default remote workspace. Check server permissions and disk space.
workspace_add_external	Add External Workspace
workspace_external_added	External workspace "%1${'$'}s" added
workspace_external_warning_title	Add External Workspace
workspace_external_warning_message	AI directly reads and writes the real files in the selected directory. Public storage does not support symlinks, so npm, yarn, pnpm, and git operations involving symlinks may fail. Removing the workspace only removes the association and does not delete directory contents.
workspace_external_warning_dont_show_again	Don’t show again
workspace_external_warning_continue	Continue
workspace_external_remove_confirm	Remove workspace "%1${'$'}s"? Only the link is removed; the directory and its files will not be deleted.
workspace_external_remove_confirm_sessions	Remove workspace "%1${'$'}s"? Only the link is removed; the directory and its files are kept, but this workspace's chats will be deleted.
workspace_external_unresolvable	Could not resolve the selected directory. Pick from the "Internal storage" or "SD card" root; other file providers are not supported
workspace_external_invalid	The selection is not a valid directory
workspace_external_unsupported_path	The directory path contains the unsupported character ":"
workspace_external_unwritable	This directory is not writable. Grant storage permission in system settings and try again
workspace_external_exists	This directory is already a workspace
workspace_external_unavailable_hint	This directory is currently unavailable (may have been moved or deleted). You can remove the record
workspace_unavailable_current	Workspace "%1${'$'}s" is currently unavailable (the directory may have been moved or deleted)
workspace_unavailable_fallback	Workspace "%1${'$'}s" is unavailable; switched to "%2${'$'}s"
container_channel_deleted	Channel deleted
chat_more_options	More Options
chat_action_edit	Edit Message
chat_action_copy	Copy Text
chat_action_fork	Fork From Here
chat_action_delete	Delete Message
chat_fork_title	Fork of %1${'$'}s
chat_edit_dialog_title	Edit Message
chat_edit_dialog_hint	Enter message content
crash_title	App encountered an unexpected error
crash_message	Something went wrong and the error has been recorded. Please copy the details below and send them to the developer to help us improve the app.
crash_copy	Copy error details
crash_copied	Error details copied
crash_restart	Restart app
crash_exit	Exit app
crash_log_hint	Full log files at: %1${'$'}s
crash_report_title	AiCode Crash Report
crash_report_time	Time: %1${'$'}s
crash_report_version	Version: %1${'$'}s
crash_report_device	Device: %1${'$'}s
crash_report_thread	Thread: %1${'$'}s
crash_report_log	Log files: %1${'$'}s
crash_report_screen	Screen: %1${'$'}s
crash_report_mode	Workspace Mode: %1${'$'}s
crash_report_memory	Memory: %1${'$'}s
crash_mode_local	Local PRoot Container
crash_mode_remote	Remote SSH
crash_mode_unknown	Unknown
crash_screen_chat	Chat
crash_screen_settings	Settings
crash_screen_terminal	Terminal
crash_screen_git	Git
crash_screen_unknown	Unknown
crash_banner_title	Application Error
crash_section_environment	Environment & Runtime
crash_section_stack	Exception Stack Trace
crash_field_workspace_mode	Workspace Mode
crash_field_screen	Screen
crash_field_version	App Version
crash_field_device	Device
crash_field_thread	Thread
crash_field_memory	Memory Usage
crash_field_time	Time
dashboard_fetching	Loading dashboard…
dashboard_fetch_failed_retry	Load failed, tap to retry
dashboard_tap_to_query	Tap to refresh
common_open_link_failed	Cannot open link
common_copied_with_title	Copied: %1${'$'}s
dashboard_exec_failed	Execution failed
dashboard_query_failed	Query failed
token_stats_hour_suffix	h
git_create_tag_title	New Tag
remote_delete_connection_confirm	Delete connection “%1${'$'}s”? Sync configurations using it will become unavailable.
remote_delete_mount_confirm	Delete workspace mapping “%1${'$'}s”? Local and remote files will not be deleted.
proxy_title	Network Proxy
proxy_enable	Enable proxy
proxy_enable_subtitle	Route App network requests and container commands through the proxy
proxy_type	Proxy type
proxy_type_http	HTTP
proxy_type_socks5	SOCKS5
proxy_test_title	Connectivity test
proxy_test	Test proxy
proxy_testing	Testing…
proxy_test_ok	Proxy works (HTTP %1${'$'}d)
proxy_test_http_error	Proxy returned unexpected status HTTP %1${'$'}d: %2${'$'}s
proxy_test_connect_failed	Failed to connect to proxy: %1${'$'}s
proxy_test_invalid_config	Enter a valid server address and port (1-65535) first
provider_proxy_off	Off
provider_multi_key_title	Multi-Key Mode
provider_multi_key_subtitle	Use several keys for one vendor and switch automatically when a key stops working
provider_multi_key_manage	Manage Keys
provider_multi_key_summary	%1${'$'}d keys · %2${'$'}s
provider_multi_key_list	Key List
provider_multi_key_item	Key #%1${'$'}d
provider_multi_key_empty	Tap to add the first key
provider_multi_key_add	Add key
provider_multi_key_weight	Scheduling weight
provider_multi_key_weight_hint	Default 1 · ≤ 0 excluded · Max 1,000,000
provider_multi_key_strategy	Selection Strategy
provider_multi_key_strategy_sequential	Sequential
provider_multi_key_strategy_sequential_desc	Always use the earliest key; move on only after the failure threshold
provider_multi_key_strategy_round_robin	Round Robin
provider_multi_key_strategy_round_robin_desc	New sessions start on rotating keys to spread quota; a session keeps its key so prompt cache still hits
provider_multi_key_failover	Failover
provider_multi_key_status_codes	Auto-Switch Status Codes
provider_multi_key_status_codes_hint	Comma-separated; leave empty for default 401,402,403,429
provider_multi_key_cooldown	Cooldown
provider_multi_key_cooldown_desc	How long a switched-away key stays out of the pool
provider_multi_key_cooldown_value	%1${'$'}d min
provider_multi_key_cooldown_off	None
proxy_test_url_label	Test URL
proxy_test_url_placeholder	https://www.google.com
proxy_server	Proxy server
proxy_host	Server address
proxy_host_placeholder	e.g. 127.0.0.1
proxy_port	Port
proxy_port_placeholder	e.g. 7890
proxy_auth	Authentication (optional)
proxy_username	Account (optional)
proxy_password	Password (optional)
proxy_no_proxy	No-proxy addresses
proxy_no_proxy_hint	Comma-separated; supports wildcards, host:port and CIDR ranges
provider_proxy_section	Proxy (optional)
provider_proxy_enable	Configure a dedicated proxy for this vendor
provider_proxy_enable_desc	When enabled, requests to this vendor use its own proxy; otherwise the global proxy applies
settings_rerun_onboarding	Re-run onboarding
onboarding_skip	Skip
onboarding_next	Next
onboarding_done	Done
onboarding_step_badge	Guide %1${'$'}d/%2${'$'}d
onboarding_open_sidebar_title	Open Sidebar
onboarding_open_sidebar_desc	Tap the top-left button to open sidebar and navigate to settings to configure models.
onboarding_enter_settings_title	Go to Settings
onboarding_enter_settings_desc	Tap to open App settings to manage AI vendors and API keys.
onboarding_config_provider_title	AI Vendors
onboarding_config_provider_desc	Tap to open the AI vendors list and start setting up your model vendors.
onboarding_provider_add_title	Add Vendor
onboarding_provider_add_desc	Tap “+” to add a vendor, supporting official vendors like OpenAI, Claude, Gemini, DeepSeek, or custom relays.
onboarding_provider_config_info_title	Configuration Info
onboarding_provider_config_info_desc	Enter your API key. Official presets come with base URLs prefilled; custom relays can set a custom Base URL.
onboarding_provider_fetch_models_title	Fetch Models
onboarding_provider_fetch_models_desc	Once the key is configured, tap “Fetch models” to sync available models.
onboarding_provider_select_fetched_model_title	Add Fetched Model
onboarding_provider_select_fetched_model_desc	Models retrieved. Tap the add button to add this model to the vendor.
onboarding_open_model_picker_title	Open Model Panel
onboarding_open_model_picker_desc	Back on home, tap the model icon next to the input box to open the selection panel.
onboarding_choose_model_in_sheet_title	Choose Model
onboarding_choose_model_in_sheet_desc	Tap the model you just added to set it for your chat session.
onboarding_send_message_title	Start Chatting
onboarding_send_message_desc	Model ready. Type your coding tasks or questions here to start collaborating with the AI assistant.
browser_title	Browser
browser_address_hint	Enter URL or search
browser_back	Back
browser_forward	Forward
browser_reload	Reload
browser_not_ready	Browser not ready. Enter a URL in the address bar to start browsing.
browser_new_tab	New tab
browser_close_tab	Close tab
browser_tab_default_title	New Tab
browser_tabs_count	Tabs (%1${'$'}d)
browser_empty_hint	Enter a URL in the address bar above to start browsing
browser_tabs_manage	Manage Tabs
browser_empty_click_hint	Tap above to enter a URL and start browsing
browser_night_mode_on	Turn off night mode
browser_night_mode_off	Turn on night mode
browser_devtools	Developer tools
browser_dialog_alert_title	Page Alert
browser_dialog_confirm_title	Page Confirmation
browser_dialog_prompt_title	Page Input
browser_menu	More options
browser_menu_title	Browser Options
browser_menu_general_section	Display & Tools
browser_night_mode_title	Night mode
browser_night_mode_desc	Dim page brightness and enable dark mode preference
browser_devtools_title	Developer tools
browser_devtools_desc	Enable mobile Eruda debug console
browser_ua_section	User-Agent
browser_ua_desc	Emulate different device environments
browser_ua_default	System default
browser_ua_default_desc	Native WebView User-Agent
browser_ua_android_phone	Android Phone
browser_ua_android_phone_desc	Chrome Mobile (Android)
browser_ua_android_tablet	Android Tablet
browser_ua_android_tablet_desc	Chrome Tablet (Android)
browser_ua_desktop_pc	Desktop PC
browser_ua_desktop_pc_desc	Chrome Desktop (Windows PC)
browser_ua_iphone	iPhone
browser_ua_iphone_desc	Mobile Safari (iOS)
browser_ua_ipad	iPad
browser_ua_ipad_desc	Tablet Safari (iPadOS)
browser_ua_custom	Custom
browser_ua_custom_desc	Specify custom User-Agent string
browser_ua_custom_dialog_title	Custom User-Agent
browser_ua_custom_input_hint	Enter full User-Agent string
browser_ua_applied_toast	User-Agent updated, page reloaded
chat_upload_video	Upload video
chat_upload_package	Upload app package
about_upstream	Upstream: AiCode
about_releases	Release notes
about_feedback	Report an issue
market_empty	No matching entries found
market_expand_detail	Click to expand
market_install	Install
market_install_failed_download	Download failed: %1${'$'}s
market_install_failed_install	Install failed: %1${'$'}s
market_install_failed_no_skill	No SKILL.md found in the archive
market_install_no_url	No download URL
market_install_success	Installed: %1${'$'}s
market_installed	Installed
market_installing	Installing %1${'$'}s…
market_load_more	Load more
market_loading_more	Loading…
market_matched	 · %1${'$'}d matched
market_refresh	Refresh
market_search_hint	Search (name / description / author)
market_sort_by_name	By name
market_sort_by_stars	By stars
market_total_skills	%1${'$'}d skills
skill_detail_author	Author
skill_detail_category	Category
skill_detail_display_name	Display name
skill_detail_download	Download URL
skill_detail_name	Name
skill_detail_package	Package
skill_detail_stars	Stars
skill_detail_tags	Tags
skill_detail_version	Version
skill_install_note	Installing extracts every SKILL.md from the archive into ~/.aicode/skills/&lt;name&gt;/
skill_market_title	Skill Market
editor_save	Save
plugin_market_title	Plugins
plugin_market_section	Available plugins
plugin_installed_section	Installed
plugin_install	Install
plugin_install_url	Install from URL
plugin_installed	Installed
plugin_search_hint	Search plugins…
plugin_empty	No plugins to show
plugin_by	By %1${'$'}s
plugin_enable	Enable
plugin_disable	Disable
plugin_delete_title	Uninstall plugin
plugin_delete_message	Uninstall “%1${'$'}s”? Related skills will also be removed.
plugin_runtime_failed	Plugin runtime execution failed
plugin_tool_not_authorized	Plugin is disabled; the tool is no longer available
plugin_tool_not_found	Tool unavailable
plugin_tool_not_found_hint	The plugin may be disabled, uninstalled, or no longer provides this tool.
plugin_tool_permission_details	Plugin: %1${'$'}s\nTool: %2${'$'}s\nArgs: %3${'$'}s
plugin_tool_permission_summary	The AI requests to call tool “%2${'$'}s” from plugin “%1${'$'}s”.
plugin_tool_permission_title	Confirm plugin tool call
plugin_tool_unavailable	Plugin is disabled; tool unavailable
plugin_web_page_failed	Failed to load page
plugin_web_page_retry	Retry
plugin_detail_title	Plugin details
plugin_page_unsupported	This plugin page requires a browser engine and is not yet supported on desktop.
plugin_store_title	Plugin Market
plugin_store_refresh	Refresh
plugin_store_install_success	Plugin installed: %1${'$'}s
plugin_update_available	Update available to v%1${'$'}s
plugin_up_to_date	Up to date
plugin_update	Update
plugin_install_resolving	Resolving version…
plugin_install_downloading	Downloading plugin…
plugin_install_installing	Installing…
plugin_install_progress	Downloaded %1${'$'}d%%
""".trimIndent()

/**
 * Int resource ids mirroring the generated entries. Used by call sites that pass a
 * computed resource id (e.g. `nameRes = R.string.x`). ids are only valid within this
 * process and have no meaning outside it.
 */
object R {
    object string {
        const val about_auto_check_update: Int = 1
        const val about_auto_check_update_desc: Int = 2
        const val about_check_failed: Int = 3
        const val about_check_update: Int = 4
        const val about_checking_update: Int = 5
        const val about_description: Int = 6
        const val about_download: Int = 7
        const val about_github_repo: Int = 8
        const val about_later: Int = 9
        const val about_license: Int = 10
        const val about_network_error: Int = 11
        const val about_new_version_found: Int = 12
        const val about_no_changelog: Int = 13
        const val about_ok: Int = 14
        const val about_qq_group: Int = 1928
        const val about_qq_group_copied: Int = 1929
        const val about_parse_version_failed: Int = 15
        const val about_up_to_date: Int = 16
        const val about_up_to_date_detail: Int = 17
        const val about_update_apk_ready: Int = 18
        const val about_update_background: Int = 19
        const val about_update_browser: Int = 20
        const val about_update_channel: Int = 21
        const val about_update_download_failed: Int = 22
        const val about_update_download_failed_hint: Int = 23
        const val about_update_download_install: Int = 24
        const val about_update_download_size: Int = 25
        const val about_update_downloading: Int = 26
        const val about_update_install: Int = 27
        const val about_update_install_failed: Int = 28
        const val about_update_mirror: Int = 29
        const val about_update_mirror_default: Int = 30
        const val about_update_mirror_desc: Int = 31
        const val about_update_mirror_invalid: Int = 32
        const val about_update_mirror_preset_cdn: Int = 33
        const val about_update_mirror_preset_direct: Int = 34
        const val about_update_mirror_preset_v6: Int = 35
        const val about_update_need_permission: Int = 36
        const val about_update_retry: Int = 37
        const val about_update_verify_not_apk: Int = 38
        const val about_update_verify_not_newer: Int = 39
        const val about_update_verify_package: Int = 40
        const val about_update_verify_signature: Int = 41
        const val about_version: Int = 42
        const val agent_compaction_failed: Int = 43
        const val agent_complete_notification_background_body: Int = 44
        const val agent_complete_notification_background_multi_body: Int = 45
        const val agent_complete_notification_body: Int = 46
        const val agent_complete_notification_title: Int = 47
        const val agent_context_no_compaction: Int = 48
        const val agent_input_budget_exceeded: Int = 49
        const val agent_model_not_selected: Int = 50
        const val agent_stop_context_exceeded: Int = 51
        const val agent_stop_refusal: Int = 52
        const val agent_stopped_by_user: Int = 53
        const val agent_tool_executing: Int = 54
        const val agreement_accept: Int = 55
        const val agreement_decline: Int = 56
        const val agreement_notice_conjunction: Int = 57
        const val agreement_notice_prefix: Int = 58
        const val agreement_notice_suffix: Int = 59
        const val agreement_privacy: Int = 60
        const val agreement_privacy_link: Int = 61
        const val agreement_privacy_url: Int = 62
        const val agreement_terms: Int = 63
        const val agreement_terms_link: Int = 64
        const val agreement_terms_url: Int = 65
        const val agreement_title: Int = 66
        const val app_name: Int = 67
        const val ask_confirm: Int = 68
        const val ask_confirm_title: Int = 69
        const val ask_custom_answer: Int = 70
        const val ask_input_hint: Int = 71
        const val ask_supplement: Int = 72
        const val backup_actions: Int = 73
        const val backup_data_app_settings: Int = 74
        const val backup_data_chat_history: Int = 75
        const val backup_data_mcp: Int = 76
        const val backup_data_permissions: Int = 77
        const val backup_data_remote: Int = 78
        const val backup_data_workspace: Int = 79
        const val backup_export_btn: Int = 80
        const val backup_export_failed: Int = 81
        const val backup_export_subtitle: Int = 82
        const val backup_export_title: Int = 83
        const val backup_exported: Int = 84
        const val backup_import_btn: Int = 85
        const val backup_import_done: Int = 86
        const val backup_import_failed: Int = 87
        const val backup_import_subtitle: Int = 88
        const val backup_import_title: Int = 89
        const val backup_net_access_key: Int = 90
        const val backup_net_add_target: Int = 91
        const val backup_net_base_path: Int = 92
        const val backup_net_base_path_hint: Int = 93
        const val backup_net_bucket: Int = 94
        const val backup_net_delete_failed: Int = 95
        const val backup_net_delete_target_confirm: Int = 96
        const val backup_net_edit_target: Int = 97
        const val backup_net_endpoint: Int = 98
        const val backup_net_endpoint_hint: Int = 99
        const val backup_net_list_failed: Int = 100
        const val backup_net_loading: Int = 101
        const val backup_net_name: Int = 102
        const val backup_net_no_target: Int = 103
        const val backup_net_no_target_hint: Int = 104
        const val backup_net_password: Int = 105
        const val backup_net_path_style: Int = 106
        const val backup_net_region: Int = 107
        const val backup_net_remote_delete: Int = 108
        const val backup_net_remote_delete_confirm: Int = 109
        const val backup_net_remote_empty: Int = 110
        const val backup_net_remote_list_title: Int = 111
        const val backup_net_remote_restore: Int = 112
        const val backup_net_restore: Int = 113
        const val backup_net_restore_subtitle: Int = 114
        const val backup_net_secret_key: Int = 115
        const val backup_net_section: Int = 116
        const val backup_net_session_token: Int = 117
        const val backup_net_targets: Int = 118
        const val backup_net_test_connection: Int = 119
        const val backup_net_test_failed: Int = 120
        const val backup_net_test_success: Int = 121
        const val backup_net_testing: Int = 122
        const val backup_net_type_s3: Int = 123
        const val backup_net_type_webdav: Int = 124
        const val backup_net_upload: Int = 125
        const val backup_net_upload_done: Int = 126
        const val backup_net_upload_failed: Int = 127
        const val backup_net_upload_password_hint: Int = 128
        const val backup_net_upload_password_required: Int = 129
        const val backup_net_upload_short: Int = 130
        const val backup_net_upload_subtitle: Int = 131
        const val backup_net_uploading: Int = 132
        const val backup_net_username: Int = 133
        const val backup_operation_failed: Int = 134
        const val backup_password_hint: Int = 135
        const val backup_password_input: Int = 136
        const val backup_password_label: Int = 137
        const val backup_password_optional_hint: Int = 138
        const val backup_processing: Int = 139
        const val backup_processing_data: Int = 140
        const val backup_read_failed: Int = 141
        const val backup_restore_workspaces_confirm: Int = 142
        const val backup_restore_workspaces_files: Int = 143
        const val backup_restore_workspaces_select_all: Int = 144
        const val backup_restore_workspaces_title: Int = 145
        const val backup_restore_workspaces_warning: Int = 146
        const val backup_restored_data: Int = 147
        const val backup_select_data: Int = 148
        const val backup_set_password: Int = 149
        const val backup_settings_covered: Int = 150
        const val backup_stat_chat_messages: Int = 151
        const val backup_stat_chat_sessions: Int = 152
        const val backup_stat_mcp_servers: Int = 153
        const val backup_stat_permission_rules: Int = 154
        const val backup_stat_providers: Int = 155
        const val backup_stat_remote_connections: Int = 156
        const val backup_stat_remote_mounts: Int = 157
        const val backup_stat_todo_items: Int = 158
        const val backup_stat_workspace_files: Int = 159
        const val backup_tab_local: Int = 160
        const val backup_workspace_git_hint: Int = 161
        const val backup_write_failed: Int = 162
        const val backup_wrong_password: Int = 163
        const val browser_address_hint: Int = 164
        const val browser_back: Int = 165
        const val browser_close_tab: Int = 166
        const val browser_devtools: Int = 167
        const val browser_devtools_desc: Int = 168
        const val browser_devtools_title: Int = 169
        const val browser_dialog_alert_title: Int = 170
        const val browser_dialog_confirm_title: Int = 171
        const val browser_dialog_prompt_title: Int = 172
        const val browser_empty_click_hint: Int = 173
        const val browser_empty_hint: Int = 174
        const val browser_forward: Int = 175
        const val browser_menu: Int = 176
        const val browser_menu_general_section: Int = 177
        const val browser_menu_title: Int = 178
        const val browser_new_tab: Int = 179
        const val browser_night_mode_desc: Int = 180
        const val browser_night_mode_off: Int = 181
        const val browser_night_mode_on: Int = 182
        const val browser_night_mode_title: Int = 183
        const val browser_not_ready: Int = 184
        const val browser_reload: Int = 185
        const val browser_tab_default_title: Int = 186
        const val browser_tabs_count: Int = 187
        const val browser_tabs_manage: Int = 188
        const val browser_title: Int = 189
        const val browser_ua_android_phone: Int = 190
        const val browser_ua_android_phone_desc: Int = 191
        const val browser_ua_android_tablet: Int = 192
        const val browser_ua_android_tablet_desc: Int = 193
        const val browser_ua_applied_toast: Int = 194
        const val browser_ua_custom: Int = 195
        const val browser_ua_custom_desc: Int = 196
        const val browser_ua_custom_dialog_title: Int = 197
        const val browser_ua_custom_input_hint: Int = 198
        const val browser_ua_default: Int = 199
        const val browser_ua_default_desc: Int = 200
        const val browser_ua_desc: Int = 201
        const val browser_ua_desktop_pc: Int = 202
        const val browser_ua_desktop_pc_desc: Int = 203
        const val browser_ua_ipad: Int = 204
        const val browser_ua_ipad_desc: Int = 205
        const val browser_ua_iphone: Int = 206
        const val browser_ua_iphone_desc: Int = 207
        const val browser_ua_section: Int = 208
        const val chat_action_copy: Int = 209
        const val chat_action_delete: Int = 210
        const val chat_action_edit: Int = 211
        const val chat_action_fork: Int = 212
        const val chat_add_attachment: Int = 213
        const val chat_approve_and_implement: Int = 214
        const val chat_attachment_max: Int = 215
        const val chat_attachment_open: Int = 216
        const val chat_attachment_prefix: Int = 217
        const val chat_attachment_preview: Int = 218
        const val chat_attachment_uploaded: Int = 219
        const val chat_attachment_uploaded_partial: Int = 220
        const val chat_attachment_uploading: Int = 221
        const val chat_batch_delete_confirm: Int = 222
        const val chat_batch_delete_title: Int = 223
        const val chat_batch_select: Int = 224
        const val chat_bg_command_done: Int = 225
        const val chat_bg_commands_done: Int = 226
        const val chat_bg_commands_partial_failed: Int = 227
        const val chat_cache_hit_rate: Int = 228
        const val chat_camera_file_failed: Int = 229
        const val chat_compaction_failed: Int = 230
        const val chat_compressing_context: Int = 231
        const val chat_connecting_remote: Int = 232
        const val chat_context_compressed: Int = 233
        const val chat_context_usage: Int = 234
        const val chat_context_usage_estimated: Int = 235
        const val chat_continue_feedback: Int = 236
        const val chat_copied: Int = 237
        const val chat_copy: Int = 238
        const val chat_delete_session: Int = 239
        const val chat_delete_session_confirm: Int = 240
        const val chat_edit_dialog_hint: Int = 241
        const val chat_edit_dialog_title: Int = 242
        const val chat_error_title: Int = 243
        const val chat_export_session: Int = 244
        const val chat_export_session_done: Int = 245
        const val chat_export_session_failed: Int = 246
        const val chat_fork_title: Int = 247
        const val chat_goal_clear: Int = 248
        const val chat_goal_label: Int = 249
        const val chat_goal_pause: Int = 250
        const val chat_goal_resume: Int = 251
        const val chat_goal_status_active: Int = 252
        const val chat_goal_status_blocked: Int = 253
        const val chat_goal_status_completed: Int = 254
        const val chat_goal_status_exhausted: Int = 255
        const val chat_goal_status_paused: Int = 256
        const val chat_history: Int = 257
        const val chat_image_too_large: Int = 258
        const val chat_input_expand: Int = 259
        const val chat_input_fullscreen_title: Int = 260
        const val chat_input_hint: Int = 261
        const val chat_input_placeholder: Int = 262
        const val chat_key_switched: Int = 263
        const val chat_mention_empty: Int = 264
        const val chat_mention_error: Int = 265
        const val chat_mention_loading: Int = 266
        const val chat_mention_unavailable: Int = 267
        const val chat_more_options: Int = 268
        const val chat_new_session: Int = 269
        const val chat_new_session_btn: Int = 270
        const val chat_no_model_selected: Int = 271
        const val chat_no_models_hint: Int = 272
        const val chat_no_remote_connection: Int = 273
        const val chat_no_sessions_hint: Int = 274
        const val chat_open_apk_permission: Int = 275
        const val chat_open_browser: Int = 276
        const val chat_open_file_missing: Int = 277
        const val chat_open_file_no_app: Int = 278
        const val chat_open_file_unshareable: Int = 279
        const val chat_open_git: Int = 280
        const val chat_open_sidebar: Int = 281
        const val chat_open_terminal: Int = 282
        const val chat_perm_always_allow: Int = 283
        const val chat_perm_always_prefix: Int = 284
        const val chat_perm_always_tool_desc: Int = 285
        const val chat_perm_deny: Int = 286
        const val chat_perm_session_label: Int = 287
        const val chat_perm_single_use_desc: Int = 288
        const val chat_pin_session: Int = 289
        const val chat_placeholder: Int = 290
        const val chat_plan_completed: Int = 291
        const val chat_queue_drag: Int = 292
        const val chat_queue_edit: Int = 293
        const val chat_queue_hint: Int = 294
        const val chat_queue_interject: Int = 295
        const val chat_queue_remove: Int = 296
        const val chat_queue_title: Int = 297
        const val chat_read_file_failed: Int = 298
        const val chat_reasoning_cannot_disable: Int = 299
        const val chat_reasoning_default_tag: Int = 300
        const val chat_reasoning_effective: Int = 301
        const val chat_reasoning_effective_tag: Int = 302
        const val chat_reasoning_effort: Int = 303
        const val chat_reasoning_effort_high: Int = 304
        const val chat_reasoning_effort_low: Int = 305
        const val chat_reasoning_effort_max: Int = 306
        const val chat_reasoning_effort_medium: Int = 307
        const val chat_reasoning_effort_minimal: Int = 308
        const val chat_reasoning_effort_none: Int = 309
        const val chat_reasoning_effort_xhigh: Int = 310
        const val chat_reasoning_endpoint_weak: Int = 311
        const val chat_reasoning_off_short: Int = 312
        const val chat_reasoning_toggle: Int = 313
        const val chat_reasoning_unknown_hint: Int = 314
        const val chat_remote_connect_failed: Int = 315
        const val chat_remove_attachment: Int = 316
        const val chat_rename_session: Int = 317
        const val chat_retrying: Int = 318
        const val chat_search_clear: Int = 319
        const val chat_search_hint: Int = 320
        const val chat_search_no_result: Int = 321
        const val chat_select_workspace_first: Int = 322
        const val chat_send: Int = 323
        const val chat_session_name: Int = 324
        const val chat_sessions_selected_count: Int = 325
        const val chat_settings: Int = 326
        const val chat_ssh_connected: Int = 327
        const val chat_ssh_connecting: Int = 328
        const val chat_ssh_disconnected: Int = 329
        const val chat_ssh_failed: Int = 330
        const val chat_status_asking_user: Int = 331
        const val chat_status_browsing_web: Int = 332
        const val chat_status_calling_tool: Int = 333
        const val chat_status_editing_file: Int = 334
        const val chat_status_generating: Int = 335
        const val chat_status_generating_image: Int = 336
        const val chat_status_reading_file: Int = 337
        const val chat_status_reading_memory: Int = 338
        const val chat_status_running_command: Int = 339
        const val chat_status_searching_web: Int = 340
        const val chat_status_starting_subagent: Int = 341
        const val chat_status_thinking: Int = 342
        const val chat_status_updating_todo: Int = 343
        const val chat_status_viewing_image: Int = 344
        const val chat_stop: Int = 345
        const val chat_take_photo: Int = 346
        const val chat_task_duration: Int = 347
        const val chat_thinking_done: Int = 348
        const val chat_thinking_done_time: Int = 349
        const val chat_thinking_process: Int = 350
        const val chat_thinking_running_time: Int = 351
        const val chat_tool_calls_count: Int = 352
        const val chat_tool_failed: Int = 353
        const val chat_tool_running: Int = 354
        const val chat_tool_succeeded: Int = 355
        const val chat_turn_completed: Int = 356
        const val chat_turn_completed_plain: Int = 357
        const val chat_turn_running: Int = 358
        const val chat_unpin_session: Int = 359
        const val chat_unsupported_image_type: Int = 360
        const val chat_upload_failed: Int = 361
        const val chat_upload_file: Int = 362
        const val chat_upload_image: Int = 363
        const val chat_uploaded_to: Int = 364
        const val chat_workspace_unavailable: Int = 365
        const val checkpoint_restore_code: Int = 366
        const val checkpoint_restore_code_and_conversation: Int = 367
        const val checkpoint_restore_code_and_conversation_desc: Int = 368
        const val checkpoint_restore_code_desc: Int = 369
        const val checkpoint_restore_conversation: Int = 370
        const val checkpoint_restore_conversation_desc: Int = 371
        const val checkpoint_rewind_title: Int = 372
        const val checkpoint_skipped_multiple: Int = 373
        const val checkpoint_skipped_single: Int = 374
        const val checkpoint_target_prompt: Int = 375
        const val common_add: Int = 376
        const val common_ai_providers: Int = 377
        const val common_allow: Int = 378
        const val common_back: Int = 379
        const val common_batch_delete: Int = 380
        const val common_batch_deleting: Int = 381
        const val common_batch_disable: Int = 382
        const val common_batch_enable: Int = 383
        const val common_batch_reconnect: Int = 384
        const val common_cancel: Int = 385
        const val common_clear: Int = 386
        const val common_close: Int = 387
        const val common_collapse: Int = 388
        const val common_collapse_action: Int = 389
        const val common_confirm: Int = 390
        const val common_copied_with_title: Int = 391
        const val common_copy: Int = 392
        const val common_copy_success: Int = 393
        const val common_create: Int = 394
        const val common_cut: Int = 395
        const val common_delete: Int = 396
        const val common_deselect_all: Int = 397
        const val common_disabled: Int = 398
        const val common_done: Int = 399
        const val common_edit: Int = 400
        const val common_enabled: Int = 401
        const val common_error: Int = 402
        const val common_error_unknown: Int = 403
        const val common_expand: Int = 404
        const val common_file: Int = 405
        const val common_go_to_settings: Int = 406
        const val common_got_it: Int = 407
        const val common_hide: Int = 408
        const val common_image_load_failed: Int = 409
        const val common_image_preview: Int = 410
        const val common_label_with_api: Int = 411
        const val common_local: Int = 412
        const val common_model: Int = 413
        const val common_more: Int = 414
        const val common_name: Int = 415
        const val common_new_tab: Int = 416
        const val common_next: Int = 417
        const val common_open_link_failed: Int = 418
        const val common_other: Int = 419
        const val common_overwrite: Int = 420
        const val common_paste: Int = 421
        const val common_prev: Int = 422
        const val common_rename: Int = 423
        const val common_reset: Int = 424
        const val common_retry: Int = 425
        const val common_save: Int = 426
        const val common_scroll_to_bottom: Int = 427
        const val common_select: Int = 428
        const val common_select_all: Int = 429
        const val common_select_mode: Int = 430
        const val common_selected_count: Int = 431
        const val common_show: Int = 432
        const val common_switch: Int = 433
        const val common_tool: Int = 434
        const val common_tool_missing_argument: Int = 435
        const val common_username: Int = 436
        const val common_workspace: Int = 437
        const val compaction_no_models: Int = 438
        const val container_add_arg: Int = 439
        const val container_add_binding: Int = 440
        const val container_add_env_var: Int = 441
        const val container_add_image: Int = 442
        const val container_announcement_got_it: Int = 443
        const val container_announcement_title: Int = 444
        const val container_arg_value: Int = 445
        const val container_builtin_badge: Int = 446
        const val container_builtin_restorable: Int = 447
        const val container_channel_deleted: Int = 448
        const val container_custom_badge: Int = 449
        const val container_custom_image: Int = 450
        const val container_default_badge: Int = 451
        const val container_default_desc: Int = 452
        const val container_delete_config: Int = 453
        const val container_delete_confirm: Int = 454
        const val container_download_architecture: Int = 455
        const val container_download_batch_delete_confirm: Int = 456
        const val container_download_batch_delete_title: Int = 457
        const val container_download_cancel: Int = 458
        const val container_download_checksum_failed: Int = 459
        const val container_download_current_source: Int = 460
        const val container_download_current_source_title: Int = 461
        const val container_download_delete_confirm: Int = 462
        const val container_download_delete_title: Int = 463
        const val container_download_done: Int = 464
        const val container_download_eol_ended: Int = 465
        const val container_download_eol_extended: Int = 466
        const val container_download_eol_extended_generic: Int = 467
        const val container_download_eol_fully_ends: Int = 468
        const val container_download_eol_soon: Int = 469
        const val container_download_eol_support_ends: Int = 470
        const val container_download_failed: Int = 471
        const val container_download_image: Int = 472
        const val container_download_import: Int = 473
        const val container_download_imported_toast: Int = 474
        const val container_download_list_empty: Int = 475
        const val container_download_newer_available: Int = 476
        const val container_download_redownload: Int = 477
        const val container_download_size: Int = 478
        const val container_download_source_unavailable: Int = 479
        const val container_download_start: Int = 480
        const val container_edit_image: Int = 481
        const val container_empty: Int = 482
        const val container_empty_desc: Int = 483
        const val container_env_name: Int = 484
        const val container_env_value: Int = 485
        const val container_env_vars: Int = 486
        const val container_extra_bindings: Int = 487
        const val container_extra_proot_args: Int = 488
        const val container_file_selected: Int = 489
        const val container_importing: Int = 490
        const val container_local_image: Int = 491
        const val container_mount_container: Int = 492
        const val container_mount_local: Int = 493
        const val container_no_args: Int = 494
        const val container_no_bindings: Int = 495
        const val container_no_env_vars: Int = 496
        const val container_no_sftp_channel: Int = 497
        const val container_not_ready_hint: Int = 498
        const val container_remote_ssh: Int = 499
        const val container_remote_workspace_path: Int = 500
        const val container_reset: Int = 501
        const val container_reset_confirm: Int = 502
        const val container_reset_title: Int = 503
        const val container_resetting_message: Int = 504
        const val container_resetting_title: Int = 505
        const val container_restore_builtin: Int = 506
        const val container_rootfs_will_be_cleared: Int = 507
        const val container_select_image_file: Int = 508
        const val container_select_ssh_channel: Int = 509
        const val container_set_as_default: Int = 510
        const val container_shell_desc: Int = 511
        const val container_shell_path: Int = 512
        const val container_sort_long_press: Int = 513
        const val container_ssh_badge: Int = 514
        const val container_ssh_channel: Int = 515
        const val container_switch_confirm: Int = 516
        const val container_switch_image: Int = 517
        const val crash_banner_title: Int = 518
        const val crash_copied: Int = 519
        const val crash_copy: Int = 520
        const val crash_exit: Int = 521
        const val crash_field_device: Int = 522
        const val crash_field_memory: Int = 523
        const val crash_field_screen: Int = 524
        const val crash_field_thread: Int = 525
        const val crash_field_time: Int = 526
        const val crash_field_version: Int = 527
        const val crash_field_workspace_mode: Int = 528
        const val crash_log_hint: Int = 529
        const val crash_message: Int = 530
        const val crash_mode_local: Int = 531
        const val crash_mode_remote: Int = 532
        const val crash_mode_unknown: Int = 533
        const val crash_report_device: Int = 534
        const val crash_report_log: Int = 535
        const val crash_report_memory: Int = 536
        const val crash_report_mode: Int = 537
        const val crash_report_screen: Int = 538
        const val crash_report_thread: Int = 539
        const val crash_report_time: Int = 540
        const val crash_report_title: Int = 541
        const val crash_report_version: Int = 542
        const val crash_restart: Int = 543
        const val crash_screen_chat: Int = 544
        const val crash_screen_git: Int = 545
        const val crash_screen_settings: Int = 546
        const val crash_screen_terminal: Int = 547
        const val crash_screen_unknown: Int = 548
        const val crash_section_environment: Int = 549
        const val crash_section_stack: Int = 550
        const val crash_title: Int = 551
        const val credential_add: Int = 552
        const val credential_add_github_desc: Int = 553
        const val credential_add_github_title: Int = 554
        const val credential_add_manual: Int = 555
        const val credential_add_manual_desc: Int = 556
        const val credential_add_manual_subtitle: Int = 557
        const val credential_add_manual_title: Int = 558
        const val credential_add_recommend: Int = 559
        const val credential_add_sheet_subtitle: Int = 560
        const val credential_add_sheet_title: Int = 561
        const val credential_auto_inject_enable: Int = 562
        const val credential_auto_inject_hint: Int = 563
        const val credential_auto_inject_prompt_body: Int = 564
        const val credential_auto_inject_prompt_title: Int = 565
        const val credential_auto_inject_title: Int = 566
        const val credential_device_authorize_and_open: Int = 567
        const val credential_device_bad_client: Int = 568
        const val credential_device_card_label: Int = 569
        const val credential_device_code_copied: Int = 570
        const val credential_device_code_copied_opening: Int = 571
        const val credential_device_copy_code: Int = 572
        const val credential_device_denied: Int = 573
        const val credential_device_expired: Int = 574
        const val credential_device_failed: Int = 575
        const val credential_device_failed_title: Int = 576
        const val credential_device_flow_disabled: Int = 577
        const val credential_device_install_later: Int = 578
        const val credential_device_install_now: Int = 579
        const val credential_device_install_required: Int = 580
        const val credential_device_instruction: Int = 581
        const val credential_device_open_browser: Int = 582
        const val credential_device_requesting: Int = 583
        const val credential_device_step_1: Int = 584
        const val credential_device_step_2: Int = 585
        const val credential_device_subtitle: Int = 586
        const val credential_device_success: Int = 587
        const val credential_device_success_title: Int = 588
        const val credential_device_tap_to_copy: Int = 589
        const val credential_device_title: Int = 590
        const val credential_device_unknown_error: Int = 591
        const val credential_device_waiting: Int = 592
        const val credential_device_waiting_desc: Int = 593
        const val credential_edit: Int = 594
        const val credential_empty_desc: Int = 595
        const val credential_empty_hint: Int = 596
        const val credential_empty_title: Int = 597
        const val credential_host: Int = 598
        const val credential_prompt_desc: Int = 599
        const val credential_prompt_title: Int = 600
        const val credential_quick_login: Int = 601
        const val credential_quick_login_desc: Int = 602
        const val credential_quick_login_github_desc: Int = 603
        const val credential_quick_login_title: Int = 604
        const val credential_remote_host: Int = 605
        const val credential_save_and_retry: Int = 606
        const val credential_toast_deleted: Int = 607
        const val credential_toast_identity_saved: Int = 608
        const val credential_toast_save_failed: Int = 609
        const val credential_toast_saved: Int = 610
        const val credential_token: Int = 611
        const val dashboard_exec_failed: Int = 612
        const val dashboard_fetch_failed_retry: Int = 613
        const val dashboard_fetching: Int = 614
        const val dashboard_query_failed: Int = 615
        const val dashboard_tap_to_query: Int = 616
        const val diff_binary_file: Int = 617
        const val diff_file_too_large: Int = 618
        const val diff_file_too_large_desc: Int = 619
        const val diff_no_changes: Int = 620
        const val drawer_collapse_subagents: Int = 621
        const val drawer_expand_subagents: Int = 622
        const val drawer_tab_files: Int = 623
        const val editor_binary_unsupported: Int = 624
        const val editor_cursor_left: Int = 625
        const val editor_cursor_right: Int = 626
        const val editor_discard: Int = 627
        const val editor_dont_save: Int = 628
        const val editor_file_too_large: Int = 629
        const val editor_font_size: Int = 630
        const val editor_font_size_value: Int = 631
        const val editor_indent: Int = 632
        const val editor_load_failed: Int = 633
        const val editor_md_preview: Int = 634
        const val editor_md_show_source: Int = 635
        const val editor_redo: Int = 636
        const val editor_save: Int = 2005
        const val editor_save_and_exit: Int = 637
        const val editor_save_and_switch: Int = 638
        const val editor_save_conflict_cancel: Int = 639
        const val editor_save_conflict_diff: Int = 640
        const val editor_save_conflict_discard: Int = 641
        const val editor_save_conflict_discarded: Int = 642
        const val editor_save_conflict_message: Int = 643
        const val editor_save_conflict_overwrite: Int = 644
        const val editor_save_conflict_title: Int = 645
        const val editor_save_failed: Int = 646
        const val editor_save_success: Int = 647
        const val editor_settings: Int = 648
        const val editor_show_indent_guide: Int = 649
        const val editor_show_whitespace: Int = 650
        const val editor_show_wrap_arrow: Int = 651
        const val editor_undo: Int = 652
        const val editor_unsaved_message: Int = 653
        const val editor_unsaved_title: Int = 654
        const val editor_word_wrap: Int = 655
        const val file_browser_clipboard_copy: Int = 656
        const val file_browser_clipboard_cut: Int = 657
        const val file_browser_collapse: Int = 658
        const val file_browser_create_failed: Int = 659
        const val file_browser_delete_failed: Int = 660
        const val file_browser_delete_file_confirm: Int = 661
        const val file_browser_delete_folder_confirm: Int = 662
        const val file_browser_empty: Int = 663
        const val file_browser_error: Int = 664
        const val file_browser_expand: Int = 665
        const val file_browser_name_label: Int = 666
        const val file_browser_new_file: Int = 667
        const val file_browser_new_folder: Int = 668
        const val file_browser_paste_conflict_message: Int = 669
        const val file_browser_paste_conflict_title: Int = 670
        const val file_browser_paste_here: Int = 671
        const val file_browser_refresh: Int = 672
    const val file_browser_set_as_workspace: Int = 1866
    const val chat_collapse_sidebar: Int = 1867
    const val prompts_help_body: Int = 1868
    const val window_minimize: Int = 1869
    const val window_maximize: Int = 1870
    const val window_close: Int = 1871
        const val file_browser_rename_failed: Int = 673
        const val file_browser_up: Int = 674
        const val ftp_address_copied: Int = 675
        const val ftp_allow_anonymous: Int = 676
        const val ftp_anonymous_confirm_message: Int = 677
        const val ftp_anonymous_confirm_title: Int = 678
        const val ftp_anonymous_desc: Int = 679
        const val ftp_auto_start: Int = 680
        const val ftp_auto_start_desc: Int = 681
        const val ftp_config_saved: Int = 682
        const val ftp_config_title: Int = 683
        const val ftp_copy_address: Int = 684
        const val ftp_error: Int = 685
        const val ftp_listen_port: Int = 686
        const val ftp_login_password: Int = 687
        const val ftp_login_username: Int = 688
        const val ftp_not_running: Int = 689
        const val ftp_password_required: Int = 690
        const val ftp_reserved_username: Int = 691
        const val ftp_running: Int = 692
        const val ftp_save_config: Int = 693
        const val ftp_usage_item_1: Int = 694
        const val ftp_usage_item_2: Int = 695
        const val ftp_usage_item_3: Int = 696
        const val ftp_usage_title: Int = 697
        const val ftp_user_cache_cleanup_failed: Int = 698
        const val git_abort_merge_confirm: Int = 699
        const val git_action_abort_merge: Int = 700
        const val git_action_clone: Int = 701
        const val git_action_commit: Int = 702
        const val git_action_copy_path: Int = 703
        const val git_action_create_branch: Int = 704
        const val git_action_create_tag: Int = 705
        const val git_action_delete_branch: Int = 706
        const val git_action_delete_dir: Int = 707
        const val git_action_delete_file: Int = 708
        const val git_action_delete_remote_branch: Int = 709
        const val git_action_delete_tag: Int = 710
        const val git_action_init: Int = 711
        const val git_action_merge: Int = 712
        const val git_action_rename_branch: Int = 713
        const val git_action_restore_file: Int = 714
        const val git_action_revert: Int = 715
        const val git_action_revert_all: Int = 716
        const val git_action_stage_all: Int = 717
        const val git_action_stash: Int = 718
        const val git_action_unstage_all: Int = 719
        const val git_action_view_diff: Int = 720
        const val git_add_to_gitignore: Int = 721
        const val git_ahead_count: Int = 722
        const val git_base_branch: Int = 723
        const val git_behind_count: Int = 724
        const val git_branch_name: Int = 725
        const val git_branch_synced: Int = 726
        const val git_checked_out: Int = 727
        const val git_clean: Int = 728
        const val git_clone_desc: Int = 729
        const val git_clone_dialog_title: Int = 730
        const val git_clone_repo: Int = 731
        const val git_clone_url_hint: Int = 732
        const val git_clone_url_label: Int = 733
        const val git_cloning_hint: Int = 734
        const val git_commit_ai_generate: Int = 735
        const val git_commit_amend: Int = 736
        const val git_commit_changes: Int = 737
        const val git_commit_count: Int = 738
        const val git_commit_dialog_title: Int = 739
        const val git_commit_generating: Int = 740
        const val git_commit_message: Int = 741
        const val git_commit_needs_identity: Int = 742
        const val git_computing_diff: Int = 743
        const val git_conflicted_count: Int = 744
        const val git_copy_full_hash: Int = 745
        const val git_copy_short_hash: Int = 746
        const val git_create_and_switch: Int = 747
        const val git_create_branch_from_commit: Int = 748
        const val git_create_tag_at_commit: Int = 749
        const val git_create_tag_desc: Int = 750
        const val git_create_tag_title: Int = 751
        const val git_create_tracking_branch: Int = 752
        const val git_credentials_and_identity: Int = 753
        const val git_credentials_count: Int = 754
        const val git_current_branch: Int = 755
        const val git_delete_branch: Int = 756
        const val git_delete_local_branch_confirm: Int = 757
        const val git_delete_remote_branch: Int = 758
        const val git_delete_remote_branch_confirm: Int = 759
        const val git_delete_tag: Int = 760
        const val git_delete_tag_confirm: Int = 761
        const val git_delete_untracked_confirm: Int = 762
        const val git_detached_head_label: Int = 763
        const val git_detached_head_warning: Int = 764
        const val git_expanded: Int = 765
        const val git_files_changed: Int = 766
        const val git_has_changes: Int = 767
        const val git_identity_desc: Int = 768
        const val git_identity_title: Int = 769
        const val git_ignore_dir: Int = 770
        const val git_ignore_extension: Int = 771
        const val git_ignore_file: Int = 772
        const val git_init_desc_primary: Int = 773
        const val git_init_desc_secondary: Int = 774
        const val git_init_repo: Int = 775
        const val git_latest_commit: Int = 776
        const val git_latest_date: Int = 777
        const val git_load_more_commits: Int = 778
        const val git_loading_branches: Int = 779
        const val git_loading_files: Int = 780
        const val git_merge_commit: Int = 781
        const val git_merge_confirm: Int = 782
        const val git_merge_into: Int = 783
        const val git_merge_no_ff: Int = 784
        const val git_merge_pending_hint: Int = 785
        const val git_merge_pending_title: Int = 786
        const val git_modified_count: Int = 787
        const val git_modified_label: Int = 788
        const val git_new: Int = 789
        const val git_new_branch: Int = 790
        const val git_new_branch_name: Int = 791
        const val git_no_branch: Int = 792
        const val git_no_branches: Int = 793
        const val git_no_checked_out_branch: Int = 794
        const val git_no_commits: Int = 795
        const val git_no_files_changed: Int = 796
        const val git_no_identity: Int = 797
        const val git_no_more_commits: Int = 798
        const val git_no_upstream: Int = 799
        const val git_not_a_repo: Int = 800
        const val git_open_in_editor: Int = 801
        const val git_pick_existing_repo: Int = 802
        const val git_pick_repo_empty: Int = 803
        const val git_pull: Int = 804
        const val git_pull_continue: Int = 805
        const val git_pull_dirty_confirm: Int = 806
        const val git_push: Int = 807
        const val git_push_keep_master: Int = 808
        const val git_push_master_confirm_desc: Int = 809
        const val git_push_master_confirm_title: Int = 810
        const val git_push_rename_to_main: Int = 811
        const val git_recent_commit: Int = 812
        const val git_refresh: Int = 813
        const val git_remote: Int = 814
        const val git_remote_url: Int = 815
        const val git_rename_branch: Int = 816
        const val git_rename_to: Int = 817
        const val git_repo_not_repo: Int = 818
        const val git_repo_selector_title: Int = 819
        const val git_repo_url_title: Int = 820
        const val git_reset_confirm: Int = 821
        const val git_reset_mixed: Int = 822
        const val git_reset_soft: Int = 823
        const val git_reset_to_commit: Int = 824
        const val git_restore_file_confirm: Int = 825
        const val git_revert_all_confirm: Int = 826
        const val git_revert_confirm_head: Int = 827
        const val git_revert_confirm_worktree: Int = 828
        const val git_search_commits_hint: Int = 829
        const val git_stage: Int = 830
        const val git_stage_all: Int = 831
        const val git_staged_count: Int = 832
        const val git_staged_label: Int = 833
        const val git_stash_apply: Int = 834
        const val git_stash_clear: Int = 835
        const val git_stash_clear_confirm: Int = 836
        const val git_stash_count: Int = 837
        const val git_stash_drop: Int = 838
        const val git_stash_drop_confirm: Int = 839
        const val git_stash_empty: Int = 840
        const val git_stash_include_untracked: Int = 841
        const val git_stash_message_hint: Int = 842
        const val git_stash_pop: Int = 843
        const val git_stash_title: Int = 844
        const val git_status_clean: Int = 845
        const val git_switch_branch: Int = 846
        const val git_switch_confirm: Int = 847
        const val git_switch_repo: Int = 848
        const val git_tab_branches: Int = 849
        const val git_tab_commits: Int = 850
        const val git_tab_credentials: Int = 851
        const val git_tab_identity: Int = 852
        const val git_tab_stash: Int = 853
        const val git_tab_status: Int = 854
        const val git_tag_name: Int = 855
        const val git_tags: Int = 856
        const val git_title: Int = 857
        const val git_toast_action_failed: Int = 858
        const val git_toast_action_refresh_failed: Int = 859
        const val git_toast_action_success: Int = 860
        const val git_toast_added_to_gitignore: Int = 861
        const val git_toast_checkout_failed: Int = 862
        const val git_toast_checkout_success: Int = 863
        const val git_toast_diff_failed: Int = 864
        const val git_toast_generate_commit_failed: Int = 865
        const val git_toast_load_branches_failed: Int = 866
        const val git_toast_load_more_failed: Int = 867
        const val git_toast_no_diff_for_commit: Int = 868
        const val git_toast_no_remote_pull: Int = 869
        const val git_toast_no_remote_push: Int = 870
        const val git_toast_not_repo: Int = 871
        const val git_toast_path_copied: Int = 872
        const val git_toast_refresh_failed: Int = 873
        const val git_toast_switch_repo_failed: Int = 874
        const val git_tracking_branch: Int = 875
        const val git_unstage: Int = 876
        const val git_unstage_all_confirm: Int = 877
        const val git_untracked_count: Int = 878
        const val git_untracked_label: Int = 879
        const val image_gen_no_models: Int = 880
        const val language_follow_system: Int = 881
        const val log_clear_filter: Int = 882
        const val log_copied: Int = 883
        const val log_copy: Int = 884
        const val log_copy_line: Int = 885
        const val log_copy_menu_title: Int = 886
        const val log_copy_message: Int = 887
        const val log_copy_with_stack: Int = 888
        const val log_file_location: Int = 889
        const val log_files: Int = 890
        const val log_follow_latest: Int = 891
        const val log_level: Int = 892
        const val log_level_desc: Int = 893
        const val log_mcp_prefix: Int = 894
        const val log_no_match: Int = 895
        const val log_search_hint: Int = 896
        const val log_search_results: Int = 897
        const val log_show_last_lines: Int = 898
        const val log_show_lines: Int = 899
        const val market_empty: Int = 1980
        const val market_expand_detail: Int = 1981
        const val market_install: Int = 1979
        const val market_install_failed_download: Int = 2002
        const val market_install_failed_install: Int = 2004
        const val market_install_failed_no_skill: Int = 2003
        const val market_install_no_url: Int = 2001
        const val market_install_success: Int = 2000
        const val market_installed: Int = 2006
        const val plugin_market_title: Int = 2007
        const val plugin_market_section: Int = 2008
        const val plugin_installed_section: Int = 2009
        const val plugin_install: Int = 2010
        const val plugin_install_url: Int = 2011
        const val plugin_installed: Int = 2012
        const val plugin_search_hint: Int = 2013
        const val plugin_empty: Int = 2014
        const val plugin_by: Int = 2015
        const val plugin_enable: Int = 2016
        const val plugin_disable: Int = 2017
        const val plugin_delete_title: Int = 2018
        const val plugin_delete_message: Int = 2019
        const val plugin_runtime_failed: Int = 2020
        const val plugin_tool_not_authorized: Int = 2021
        const val plugin_tool_not_found: Int = 2022
        const val plugin_tool_not_found_hint: Int = 2023
        const val plugin_tool_permission_details: Int = 2024
        const val plugin_tool_permission_summary: Int = 2025
        const val plugin_tool_permission_title: Int = 2026
        const val plugin_tool_unavailable: Int = 2027
        const val plugin_web_page_failed: Int = 2028
        const val plugin_web_page_retry: Int = 2029
        const val plugin_detail_title: Int = 2030
        const val plugin_page_unsupported: Int = 2031
        const val plugin_store_title: Int = 2032
        const val plugin_store_refresh: Int = 2033
        const val plugin_store_install_success: Int = 2034
        const val plugin_update_available: Int = 2035
        const val plugin_up_to_date: Int = 2036
        const val plugin_update: Int = 2037
        const val plugin_install_resolving: Int = 2038
        const val plugin_install_downloading: Int = 2039
        const val plugin_install_installing: Int = 2040
        const val plugin_install_progress: Int = 2041
        const val market_installing: Int = 1999
        const val market_load_more: Int = 1982
        const val market_loading_more: Int = 1983
        const val market_matched: Int = 1988
        const val market_refresh: Int = 1984
        const val market_search_hint: Int = 1978
        const val market_sort_by_name: Int = 1986
        const val market_sort_by_stars: Int = 1985
        const val market_total_skills: Int = 1987
        const val mcp_add: Int = 900
        const val mcp_add_arg: Int = 901
        const val mcp_add_env_var: Int = 902
        const val mcp_add_from_json: Int = 903
        const val mcp_add_from_json_desc: Int = 904
        const val mcp_add_header: Int = 905
        const val mcp_add_manual: Int = 906
        const val mcp_add_manual_desc: Int = 907
        const val mcp_add_sheet_title: Int = 908
        const val mcp_arg_hint: Int = 909
        const val mcp_arg_value: Int = 910
        const val mcp_args: Int = 911
        const val mcp_auth_advanced: Int = 912
        const val mcp_auth_cancel: Int = 913
        const val mcp_auth_client_id: Int = 914
        const val mcp_auth_client_id_hint: Int = 915
        const val mcp_auth_desc: Int = 916
        const val mcp_auth_enable: Int = 917
        const val mcp_auth_failed: Int = 918
        const val mcp_auth_in_progress: Int = 919
        const val mcp_auth_in_progress_hint: Int = 920
        const val mcp_auth_login: Int = 921
        const val mcp_auth_logout: Int = 922
        const val mcp_auth_reauthorize: Int = 923
        const val mcp_auth_save_first: Int = 924
        const val mcp_auth_server: Int = 925
        const val mcp_auth_server_hint: Int = 926
        const val mcp_auth_status_authorized: Int = 927
        const val mcp_auth_status_authorizing: Int = 928
        const val mcp_auth_status_label: Int = 929
        const val mcp_auth_status_unauthorized: Int = 930
        const val mcp_auth_success: Int = 931
        const val mcp_basic_settings: Int = 932
        const val mcp_batch_delete_confirm: Int = 933
        const val mcp_batch_delete_title: Int = 934
        const val mcp_command: Int = 935
        const val mcp_command_hint: Int = 936
        const val mcp_connected: Int = 937
        const val mcp_connecting: Int = 938
        const val mcp_connection_failed: Int = 939
        const val mcp_custom_headers: Int = 940
        const val mcp_details: Int = 941
        const val mcp_disabled: Int = 942
        const val mcp_edit: Int = 943
        const val mcp_empty: Int = 944
        const val mcp_empty_hint: Int = 945
        const val mcp_enabled: Int = 946
        const val mcp_env_name: Int = 947
        const val mcp_env_name_hint: Int = 948
        const val mcp_env_value: Int = 949
        const val mcp_env_value_hint: Int = 950
        const val mcp_env_vars: Int = 951
        const val mcp_header_name: Int = 952
        const val mcp_header_name_hint: Int = 953
        const val mcp_header_value: Int = 954
        const val mcp_header_value_hint: Int = 955
        const val mcp_json_import_action: Int = 956
        const val mcp_json_import_empty: Int = 957
        const val mcp_json_import_hint: Int = 958
        const val mcp_json_import_title: Int = 959
        const val mcp_local_stdio: Int = 960
        const val mcp_name_hint: Int = 961
        const val mcp_name_invalid: Int = 962
        const val mcp_needs_auth: Int = 963
        const val mcp_no_args: Int = 964
        const val mcp_no_custom_headers: Int = 965
        const val mcp_no_description: Int = 966
        const val mcp_no_env_vars: Int = 967
        const val mcp_no_tools_hint: Int = 968
        const val mcp_not_connected: Int = 969
        const val mcp_refresh_tools: Int = 970
        const val mcp_remote_http: Int = 971
        const val mcp_save_first_hint: Int = 972
        const val mcp_scope_global: Int = 973
        const val mcp_scope_label: Int = 974
        const val mcp_scope_project: Int = 975
        const val mcp_search_empty: Int = 976
        const val mcp_search_placeholder: Int = 977
        const val mcp_server_url: Int = 978
        const val mcp_server_url_hint: Int = 979
        const val mcp_tools_count: Int = 980
        const val mcp_tools_disable_all: Int = 981
        const val mcp_tools_enable_all: Int = 982
        const val mcp_tools_search: Int = 983
        const val mcp_tools_search_empty: Int = 984
        const val mcp_tools_summary: Int = 985
        const val mcp_transport_type: Int = 986
        const val mcp_type_stdio: Int = 987
        const val mcp_view_logs: Int = 988
        const val network_error_connect: Int = 989
        const val network_error_dns: Int = 990
        const val network_error_ssl: Int = 991
        const val network_error_timeout: Int = 992
        const val notification_agent_live_title_multi: Int = 993
        const val notification_approval_action_allow: Int = 994
        const val notification_approval_action_approve_plan: Int = 995
        const val notification_approval_action_reject: Int = 996
        const val notification_approval_label_permission: Int = 997
        const val notification_approval_label_plan: Int = 998
        const val notification_approval_label_question: Int = 999
        const val notification_approval_multi_title: Int = 1000
        const val notification_approval_needed: Int = 1001
        const val notification_approval_title: Int = 1002
        const val notification_channel_agent_approval: Int = 1003
        const val notification_channel_agent_approval_desc: Int = 1004
        const val notification_channel_agent_complete: Int = 1005
        const val notification_channel_agent_complete_desc: Int = 1006
        const val notification_channel_agent_live: Int = 1007
        const val notification_channel_agent_live_desc: Int = 1008
        const val notification_channel_terminal_service: Int = 1009
        const val notification_channel_terminal_service_desc: Int = 1010
        const val notification_keepalive_enabled: Int = 1011
        const val notification_keepalive_persistent_sessions: Int = 1012
        const val notification_keepalive_running_sessions: Int = 1013
        const val onboarding_choose_model_in_sheet_desc: Int = 1014
        const val onboarding_choose_model_in_sheet_title: Int = 1015
        const val onboarding_config_provider_desc: Int = 1016
        const val onboarding_config_provider_title: Int = 1017
        const val onboarding_done: Int = 1018
        const val onboarding_enter_settings_desc: Int = 1019
        const val onboarding_enter_settings_title: Int = 1020
        const val onboarding_next: Int = 1021
        const val onboarding_open_model_picker_desc: Int = 1022
        const val onboarding_open_model_picker_title: Int = 1023
        const val onboarding_open_sidebar_desc: Int = 1024
        const val onboarding_open_sidebar_title: Int = 1025
        const val onboarding_provider_add_desc: Int = 1026
        const val onboarding_provider_add_title: Int = 1027
        const val onboarding_provider_config_info_desc: Int = 1028
        const val onboarding_provider_config_info_title: Int = 1029
        const val onboarding_provider_fetch_models_desc: Int = 1030
        const val onboarding_provider_fetch_models_title: Int = 1031
        const val onboarding_provider_select_fetched_model_desc: Int = 1032
        const val onboarding_provider_select_fetched_model_title: Int = 1033
        const val onboarding_send_message_desc: Int = 1034
        const val onboarding_send_message_title: Int = 1035
        const val onboarding_skip: Int = 1036
        const val onboarding_step_badge: Int = 1037
        const val perm_current_project: Int = 1038
        const val perm_current_project_none: Int = 1039
        const val perm_deny: Int = 1040
        const val perm_disable_safety_interception: Int = 1041
        const val perm_disable_safety_interception_desc: Int = 1042
        const val perm_entire_tool: Int = 1043
        const val perm_global: Int = 1044
        const val perm_no_global_rules: Int = 1045
        const val perm_no_project_rules: Int = 1046
        const val perm_promote_to_global: Int = 1047
        const val perm_rules_short: Int = 1048
        const val perm_safety_confirm_action: Int = 1049
        const val perm_safety_confirm_action_ready: Int = 1050
        const val perm_safety_confirm_message: Int = 1051
        const val perm_safety_confirm_title: Int = 1052
        const val perm_safety_group: Int = 1053
        const val plan_mode_already_active: Int = 1054
        const val plan_mode_approved_auto: Int = 1055
        const val plan_mode_approved_build: Int = 1056
        const val plan_mode_enter_action: Int = 1057
        const val plan_mode_entered: Int = 1058
        const val plan_mode_exit_action: Int = 1059
        const val plan_mode_exited: Int = 1060
        const val plan_mode_external_auto: Int = 1061
        const val plan_mode_external_build: Int = 1062
        const val plan_mode_goal_active: Int = 1063
        const val plan_mode_invalid_action: Int = 1064
        const val plan_mode_not_active: Int = 1065
        const val plan_mode_reason_unspecified: Int = 1066
        const val plan_mode_refinement_requested: Int = 1067
        const val plan_mode_request_details: Int = 1068
        const val plan_mode_request_summary: Int = 1069
        const val plan_mode_request_title: Int = 1070
        const val plan_mode_session_missing: Int = 1071
        const val plan_mode_session_required: Int = 1072
        const val prompts_add_prompt: Int = 1073
        const val prompts_batch_delete_confirm: Int = 1074
        const val prompts_batch_delete_title: Int = 1075
        const val prompts_disable_builtin: Int = 1076
        const val prompts_disable_builtin_hint: Int = 1077
        const val prompts_editor_edit: Int = 1078
        const val prompts_editor_new: Int = 1079
        const val prompts_empty: Int = 1080
        const val prompts_field_basic: Int = 1081
        const val prompts_field_content: Int = 1082
        const val prompts_field_content_placeholder: Int = 1083
        const val prompts_field_number: Int = 1084
        const val prompts_field_number_placeholder: Int = 1085
        const val prompts_field_summary: Int = 1086
        const val prompts_field_summary_placeholder: Int = 1087
        const val prompts_field_title: Int = 1088
        const val prompts_field_title_placeholder: Int = 1089
        const val prompts_help: Int = 1090
        const val prompts_reset_confirm: Int = 1091
        const val prompts_reset_custom: Int = 1092
        const val prompts_sort_long_press: Int = 1093
        const val prompts_source_builtin: Int = 1094
        const val prompts_source_global: Int = 1095
        const val prompts_source_local: Int = 1096
        const val prompts_source_project: Int = 1097
        const val prompts_title: Int = 1098
        const val provider_add: Int = 1099
        const val provider_add_all_models: Int = 1100
        const val provider_add_all_models_confirm: Int = 1101
        const val provider_add_all_models_title: Int = 1102
        const val provider_add_model: Int = 1103
        const val provider_add_sheet_title: Int = 1104
        const val provider_api_key: Int = 1105
        const val provider_api_key_required: Int = 1106
        const val provider_base_url: Int = 1107
        const val provider_brand_doubao: Int = 1108
        const val provider_brand_hunyuan: Int = 1109
        const val provider_brand_siliconflow: Int = 1110
        const val provider_brand_tongyi_qianwen: Int = 1111
        const val provider_brand_zhipu: Int = 1112
        const val provider_cache_anthropic_subtitle: Int = 1113
        const val provider_cache_anthropic_title: Int = 1114
        const val provider_cache_openai_chat_subtitle: Int = 1115
        const val provider_cache_openai_chat_title: Int = 1116
        const val provider_config: Int = 1117
        const val provider_custom: Int = 1118
        const val provider_dashboard_clear_script: Int = 1119
        const val provider_dashboard_loading: Int = 1120
        const val provider_dashboard_no_scripts: Int = 1121
        const val provider_dashboard_preview_title: Int = 1122
        const val provider_dashboard_raw_output: Int = 1123
        const val provider_dashboard_run_test: Int = 1124
        const val provider_dashboard_script: Int = 1125
        const val provider_dashboard_script_desc: Int = 1126
        const val provider_dashboard_script_placeholder: Int = 1127
        const val provider_dashboard_select_script: Int = 1128
        const val provider_dashboard_select_title: Int = 1129
        const val provider_dashboard_test_btn: Int = 1130
        const val provider_dashboard_test_failed: Int = 1131
        const val provider_edit: Int = 1132
        const val provider_edit_model: Int = 1133
        const val provider_fetch_failed: Int = 1134
        const val provider_fetch_http_error: Int = 1135
        const val provider_fetch_missing_fields: Int = 1136
        const val provider_fetch_missing_fields_detail: Int = 1137
        const val provider_fetch_models: Int = 1138
        const val provider_fetch_success: Int = 1139
        const val provider_filter_models_hint: Int = 1140
        const val provider_full_url: Int = 1141
        const val provider_full_url_desc: Int = 1142
        const val provider_headers_add: Int = 1143
        const val provider_headers_count: Int = 1144
        const val provider_headers_desc: Int = 1145
        const val provider_headers_empty: Int = 1146
        const val provider_headers_manage: Int = 1147
        const val provider_headers_name: Int = 1148
        const val provider_headers_name_hint: Int = 1149
        const val provider_headers_remove: Int = 1150
        const val provider_headers_title: Int = 1151
        const val provider_headers_value: Int = 1152
        const val provider_headers_value_hint: Int = 1153
        const val provider_hide_api_key: Int = 1154
        const val provider_interactions_api: Int = 1155
        const val provider_model_already_added: Int = 1156
        const val provider_model_cap_image_output: Int = 1157
        const val provider_model_cap_vision: Int = 1158
        const val provider_model_capabilities: Int = 1159
        const val provider_model_capability_reasoning: Int = 1160
        const val provider_model_capability_tools: Int = 1161
        const val provider_model_context_input: Int = 1162
        const val provider_model_context_output: Int = 1163
        const val provider_model_context_total: Int = 1164
        const val provider_model_icon: Int = 1165
        const val provider_model_name: Int = 1166
        const val provider_model_price_cache_read: Int = 1167
        const val provider_model_price_cache_write: Int = 1168
        const val provider_model_price_input: Int = 1169
        const val provider_model_price_output: Int = 1170
        const val provider_model_reasoning_can_disable: Int = 1171
        const val provider_model_reasoning_hint: Int = 1172
        const val provider_model_section_price: Int = 1173
        const val provider_model_section_reasoning: Int = 1174
        const val provider_model_window_invalid: Int = 1175
        const val provider_models_aligned_removed: Int = 1176
        const val provider_models_batch_delete: Int = 1177
        const val provider_models_batch_delete_confirm: Int = 1178
        const val provider_models_batch_delete_title: Int = 1179
        const val provider_models_batch_test: Int = 1180
        const val provider_models_count: Int = 1181
        const val provider_models_count_tag: Int = 1182
        const val provider_models_deselect_all: Int = 1183
        const val provider_models_select: Int = 1184
        const val provider_models_select_all: Int = 1185
        const val provider_models_selected_count: Int = 1186
        const val provider_multi_key_add: Int = 1187
        const val provider_multi_key_cooldown: Int = 1188
        const val provider_multi_key_cooldown_desc: Int = 1189
        const val provider_multi_key_cooldown_off: Int = 1190
        const val provider_multi_key_cooldown_value: Int = 1191
        const val provider_multi_key_empty: Int = 1192
        const val provider_multi_key_failover: Int = 1193
        const val provider_multi_key_item: Int = 1194
        const val provider_multi_key_list: Int = 1195
        const val provider_multi_key_manage: Int = 1196
        const val provider_multi_key_status_codes: Int = 1197
        const val provider_multi_key_status_codes_hint: Int = 1198
        const val provider_multi_key_strategy: Int = 1199
        const val provider_multi_key_strategy_round_robin: Int = 1200
        const val provider_multi_key_strategy_round_robin_desc: Int = 1201
        const val provider_multi_key_strategy_sequential: Int = 1202
        const val provider_multi_key_strategy_sequential_desc: Int = 1203
        const val provider_multi_key_subtitle: Int = 1204
        const val provider_multi_key_summary: Int = 1205
        const val provider_multi_key_title: Int = 1206
        const val provider_multi_key_weight: Int = 1207
        const val provider_multi_key_weight_hint: Int = 1208
        const val provider_new: Int = 1209
        const val provider_no_matching_models: Int = 1210
        const val provider_no_models: Int = 1211
        const val provider_no_models_hint: Int = 1212
        const val provider_please_wait: Int = 1213
        const val provider_preset_empty: Int = 1214
        const val provider_preset_loading: Int = 1215
        const val provider_proxy_enable: Int = 1216
        const val provider_proxy_enable_desc: Int = 1217
        const val provider_proxy_off: Int = 1218
        const val provider_proxy_section: Int = 1219
        const val provider_register_now: Int = 1220
        const val provider_response_api: Int = 1221
        const val provider_script_param_add: Int = 1222
        const val provider_script_param_key: Int = 1223
        const val provider_script_param_key_hint: Int = 1224
        const val provider_script_param_manage: Int = 1225
        const val provider_script_param_remove: Int = 1226
        const val provider_script_param_value: Int = 1227
        const val provider_script_param_value_hint: Int = 1228
        const val provider_script_params_count: Int = 1229
        const val provider_script_params_desc: Int = 1230
        const val provider_script_params_empty: Int = 1231
        const val provider_script_params_title: Int = 1232
        const val provider_section_basic: Int = 1233
        const val provider_section_dashboard: Int = 1234
        const val provider_section_options: Int = 1235
        const val provider_section_type: Int = 1236
        const val provider_show_api_key: Int = 1237
        const val provider_sort_long_press: Int = 1238
        const val provider_test: Int = 1239
        const val provider_test_detail_title: Int = 1240
        const val provider_test_error_detail: Int = 1241
        const val provider_test_req_body: Int = 1242
        const val provider_test_req_headers: Int = 1243
        const val provider_test_req_url: Int = 1244
        const val provider_test_resp_body: Int = 1245
        const val provider_test_resp_headers: Int = 1246
        const val provider_test_success: Int = 1247
        const val providers_batch_delete_confirm: Int = 1248
        const val providers_batch_delete_title: Int = 1249
        const val providers_empty: Int = 1250
        const val proxy_auth: Int = 1251
        const val proxy_enable: Int = 1252
        const val proxy_enable_subtitle: Int = 1253
        const val proxy_host: Int = 1254
        const val proxy_host_placeholder: Int = 1255
        const val proxy_no_proxy: Int = 1256
        const val proxy_no_proxy_hint: Int = 1257
        const val proxy_password: Int = 1258
        const val proxy_port: Int = 1259
        const val proxy_port_placeholder: Int = 1260
        const val proxy_server: Int = 1261
        const val proxy_test: Int = 1262
        const val proxy_test_connect_failed: Int = 1263
        const val proxy_test_http_error: Int = 1264
        const val proxy_test_invalid_config: Int = 1265
        const val proxy_test_ok: Int = 1266
        const val proxy_test_title: Int = 1267
        const val proxy_test_url_label: Int = 1268
        const val proxy_test_url_placeholder: Int = 1269
        const val proxy_testing: Int = 1270
        const val proxy_title: Int = 1271
        const val proxy_type: Int = 1272
        const val proxy_type_http: Int = 1273
        const val proxy_type_socks5: Int = 1274
        const val proxy_username: Int = 1275
        const val remote_add_channel_first: Int = 1276
        const val remote_add_connection: Int = 1277
        const val remote_add_workspace: Int = 1278
        const val remote_auth_key: Int = 1279
        const val remote_auth_password: Int = 1280
        const val remote_auto_connect_and_sync: Int = 1281
        const val remote_auto_connect_on_start: Int = 1282
        const val remote_browse_dir: Int = 1283
        const val remote_clear_index_done: Int = 1284
        const val remote_confirm_select: Int = 1285
        const val remote_connect_failed: Int = 1286
        const val remote_connect_success: Int = 1287
        const val remote_connection_name_hint: Int = 1288
        const val remote_connection_sort_long_press: Int = 1289
        const val remote_current_path: Int = 1290
        const val remote_delete_connection_confirm: Int = 1291
        const val remote_delete_mount_confirm: Int = 1292
        const val remote_download_all_failed: Int = 1293
        const val remote_download_all_success: Int = 1294
        const val remote_edit_connection: Int = 1295
        const val remote_edit_workspace: Int = 1296
        const val remote_go_add: Int = 1297
        const val remote_hint_title: Int = 1298
        const val remote_host_address: Int = 1299
        const val remote_key_passphrase: Int = 1300
        const val remote_link_channel: Int = 1301
        const val remote_load_failed: Int = 1302
        const val remote_login_key: Int = 1303
        const val remote_map_to_local: Int = 1304
        const val remote_mount_already_exists: Int = 1305
        const val remote_no_connections: Int = 1306
        const val remote_no_connections_desc: Int = 1307
        const val remote_no_keys_hint: Int = 1308
        const val remote_no_local_workspace: Int = 1309
        const val remote_no_workspaces: Int = 1310
        const val remote_no_workspaces_desc: Int = 1311
        const val remote_parent_dir: Int = 1312
        const val remote_password: Int = 1313
        const val remote_port: Int = 1314
        const val remote_select_channel: Int = 1315
        const val remote_select_key: Int = 1316
        const val remote_select_local_workspace: Int = 1317
        const val remote_select_remote_dir: Int = 1318
        const val remote_tab_connections: Int = 1319
        const val remote_tab_ftp: Int = 1320
        const val remote_tab_mounts: Int = 1321
        const val remote_tab_sync: Int = 1322
        const val remote_target_dir: Int = 1323
        const val remote_test_connection: Int = 1324
        const val remote_toggle_password: Int = 1325
        const val remote_unknown_error: Int = 1326
        const val remote_upload_failed: Int = 1327
        const val remote_upload_full_failed: Int = 1328
        const val remote_upload_full_success: Int = 1329
        const val remote_upload_success: Int = 1330
        const val remote_workspace_title: Int = 1331
        const val retry_error_connection_refused: Int = 1332
        const val retry_error_connection_reset: Int = 1333
        const val retry_error_dns_failed: Int = 1334
        const val retry_error_network: Int = 1335
        const val retry_error_rate_limit: Int = 1336
        const val retry_error_server: Int = 1337
        const val retry_error_server_overloaded: Int = 1338
        const val retry_error_ssl: Int = 1339
        const val retry_error_timeout: Int = 1340
        const val retry_error_unknown: Int = 1341
        const val retry_error_with_code: Int = 1342
        const val session_group_last_30_days: Int = 1343
        const val session_group_last_7_days: Int = 1344
        const val session_group_month_format: Int = 1345
        const val session_group_pinned: Int = 1346
        const val session_group_today: Int = 1347
        const val session_group_yesterday: Int = 1348
        const val settings_about: Int = 1349
        const val settings_add_mcp_server: Int = 1350
        const val settings_add_provider: Int = 1351
        const val settings_agent_sound_subtitle: Int = 1352
        const val settings_agent_sound_title: Int = 1353
        const val settings_app_permissions: Int = 1354
        const val settings_approval_notification_subtitle: Int = 1355
        const val settings_approval_notification_title: Int = 1356
        const val settings_auto_remove_stale_models: Int = 1357
        const val settings_auto_remove_stale_models_desc: Int = 1358
        const val settings_autostart_subtitle: Int = 1359
        const val settings_autostart_title: Int = 1360
        const val settings_background_image: Int = 1361
        const val settings_background_image_alpha: Int = 1362
        const val settings_background_image_none: Int = 1363
        const val settings_background_image_pick: Int = 1364
        const val settings_background_image_remove: Int = 1365
        const val team_member_add: Int = 1927
        const val team_editor_add_agent: Int = 1925
        const val team_editor_add_model: Int = 1926
        const val settings_background_kind_gif: Int = 1872
        const val settings_background_kind_video: Int = 1873
        const val settings_teams: Int = 1881
        const val chat_open_teams: Int = 1882
        const val teams_new: Int = 1883
        const val teams_empty: Int = 1884
        const val teams_empty_hint: Int = 1885
        const val teams_list_header: Int = 1886
        const val teams_member_count: Int = 1887
        const val team_editor_new: Int = 1888
        const val team_editor_edit: Int = 1889
        const val team_editor_basic: Int = 1890
        const val team_editor_owner: Int = 1891
        const val team_editor_owner_name: Int = 1892
        const val team_editor_pick_avatar: Int = 1893
        const val team_editor_members: Int = 1894
        const val team_editor_members_hint: Int = 1895
        const val team_editor_rename_member: Int = 1896
        const val team_editor_remove_member: Int = 1897
        const val team_editor_autocompose: Int = 1898
        const val team_editor_autocompose_action: Int = 1899
        const val team_editor_autocompose_placeholder: Int = 1900
        const val team_editor_autocomposing: Int = 1901
        const val team_editor_no_agents: Int = 1902
        const val team_editor_no_models: Int = 1903
        const val team_editor_no_members: Int = 1904
        const val team_member_lead: Int = 1905
        const val team_member_prompt_hint: Int = 1906
        const val team_name_hint: Int = 1907
        const val team_description_hint: Int = 1908
        const val team_strategy_hint: Int = 1909
        const val team_error_invalid_name: Int = 1910
        const val team_error_name_conflict: Int = 1911
        const val team_error_member_limit: Int = 1912
        const val team_error_no_members: Int = 1913
        const val team_delete: Int = 1914
        const val team_delete_confirm: Int = 1915
        const val team_chat_title: Int = 1916
        const val team_chat_input_hint: Int = 1917
        const val team_chat_send: Int = 1918
        const val team_chat_stop: Int = 1919
        const val team_chat_thinking: Int = 1920
        const val team_chat_empty: Int = 1921
        const val team_chat_empty_hint: Int = 1922
        const val team_chat_clear: Int = 1923
        const val team_chat_clear_confirm: Int = 1924
        const val settings_backup: Int = 1366
        const val settings_category_ai: Int = 1367
        const val settings_category_background: Int = 1368
        const val settings_category_data: Int = 1369
        const val settings_category_environment: Int = 1370
        const val settings_category_general: Int = 1371
        const val settings_category_help: Int = 1372
        const val settings_category_permissions: Int = 1373
        const val settings_category_system_permissions: Int = 1374
        const val settings_compaction_follow_chat: Int = 1375
        const val settings_compaction_model: Int = 1376
        const val settings_compaction_threshold: Int = 1377
        const val settings_compaction_threshold_desc: Int = 1378
        const val settings_compaction_threshold_input_hint: Int = 1379
        const val settings_container: Int = 1380
        const val settings_default_models: Int = 1381
        const val settings_delete_external_workspace_sessions: Int = 1382
        const val settings_delete_external_workspace_sessions_desc: Int = 1383
        const val settings_enter_to_send: Int = 1384
        const val settings_enter_to_send_desc: Int = 1385
        const val settings_first_byte_timeout: Int = 1386
        const val settings_first_byte_timeout_desc: Int = 1387
        const val settings_general: Int = 1388
        const val settings_general_goal: Int = 1389
        const val settings_general_input: Int = 1390
        const val settings_general_models: Int = 1391
        const val settings_general_network: Int = 1392
        const val settings_general_session: Int = 1393
        const val settings_general_tools: Int = 1394
        const val settings_goal_max_tokens: Int = 1395
        const val settings_goal_max_tokens_desc: Int = 1396
        const val settings_goal_max_tokens_input_hint: Int = 1397
        const val settings_goal_max_turns: Int = 1398
        const val settings_goal_max_turns_desc: Int = 1399
        const val settings_goal_max_turns_input_hint: Int = 1400
        const val settings_image_gen_model: Int = 1401
        const val settings_image_gen_unconfigured: Int = 1402
        const val settings_keepalive_subtitle: Int = 1403
        const val settings_keepalive_title: Int = 1404
        const val settings_language: Int = 1405
        const val settings_live_notification_subtitle: Int = 1406
        const val settings_live_notification_title: Int = 1407
        const val settings_log: Int = 1408
        const val settings_log_no_files: Int = 1409
        const val settings_log_read_failed: Int = 1410
        const val settings_max_network_retries: Int = 1411
        const val settings_max_network_retries_desc: Int = 1412
        const val settings_mb_value: Int = 1413
        const val settings_mcp: Int = 1414
        const val settings_models_fetch_failed: Int = 1415
        const val settings_network_guide_brand_huawei: Int = 1416
        const val settings_network_guide_brand_oppo: Int = 1417
        const val settings_network_guide_brand_samsung: Int = 1418
        const val settings_network_guide_brand_vivo: Int = 1419
        const val settings_network_guide_brand_xiaomi: Int = 1420
        const val settings_network_guide_intro: Int = 1421
        const val settings_network_guide_more: Int = 1422
        const val settings_network_guide_step_battery: Int = 1423
        const val settings_network_guide_step_keepalive: Int = 1424
        const val settings_network_guide_step_lock: Int = 1425
        const val settings_network_guide_subtitle: Int = 1426
        const val settings_network_guide_title: Int = 1427
        const val settings_percent_value: Int = 1428
        const val settings_permission_apk_install: Int = 1429
        const val settings_permission_apk_install_desc: Int = 1430
        const val settings_permission_battery: Int = 1431
        const val settings_permission_battery_desc: Int = 1432
        const val settings_permission_denied: Int = 1433
        const val settings_permission_granted: Int = 1434
        const val settings_permission_notification_denied_desc: Int = 1435
        const val settings_permission_notification_title: Int = 1436
        const val settings_permission_storage: Int = 1437
        const val settings_permission_storage_denied_desc: Int = 1438
        const val settings_permission_storage_desc: Int = 1439
        const val settings_permissions: Int = 1440
        const val settings_provider_editor: Int = 1441
        const val settings_providers: Int = 1442
        const val settings_reconnect: Int = 1443
        const val settings_refresh_logs: Int = 1444
        const val settings_remote_servers: Int = 1445
        const val settings_rerun_onboarding: Int = 1446
        const val settings_retries_count: Int = 1447
        const val settings_retries_input_hint: Int = 1448
        const val settings_screen_on_subtitle: Int = 1449
        const val settings_screen_on_title: Int = 1450
        const val settings_select_section_hint: Int = 1451
        const val settings_sendfile_max_size: Int = 1452
        const val settings_sendfile_max_size_desc: Int = 1453
        const val settings_sendfile_max_size_input_hint: Int = 1454
        const val settings_shizuku: Int = 1455
        const val settings_shizuku_hint_denied: Int = 1456
        const val settings_shizuku_hint_not_installed: Int = 1457
        const val settings_shizuku_hint_not_running: Int = 1458
        const val settings_shizuku_hint_ready: Int = 1459
        const val settings_shizuku_status_denied: Int = 1460
        const val settings_shizuku_status_not_installed: Int = 1461
        const val settings_shizuku_status_not_running: Int = 1462
        const val settings_shizuku_status_ready: Int = 1463
        const val settings_skills: Int = 1464
        const val settings_startup_session: Int = 1465
        const val settings_startup_session_new: Int = 1466
        const val settings_startup_session_new_desc: Int = 1467
        const val settings_startup_session_recent: Int = 1468
        const val settings_startup_session_recent_desc: Int = 1469
        const val settings_storage: Int = 1470
        const val settings_stream_idle_timeout: Int = 1471
        const val settings_stream_idle_timeout_desc: Int = 1472
        const val settings_subagent_tier_light: Int = 1473
        const val settings_subagent_tier_standard: Int = 1474
        const val settings_subagent_tier_strong: Int = 1475
        const val settings_subagent_tier_unconfigured: Int = 1476
        const val settings_subagent_tiers_header: Int = 1477
        const val settings_subagents: Int = 1478
        const val settings_theme_title: Int = 1479
        const val settings_timeout_input_hint: Int = 1480
        const val settings_timeout_seconds: Int = 1481
        const val settings_timeout_unlimited: Int = 1482
        const val settings_title: Int = 1483
        const val settings_title_follow_chat: Int = 1484
        const val settings_title_model: Int = 1485
        const val settings_token_stats_cache_hit_rate: Int = 1486
        const val settings_token_stats_cache_read_write: Int = 1487
        const val settings_token_stats_calls_suffix: Int = 1488
        const val settings_token_stats_calls_suffix2: Int = 1489
        const val settings_token_stats_col_cache_hit_rate: Int = 1490
        const val settings_token_stats_col_cached_read: Int = 1491
        const val settings_token_stats_col_cached_write: Int = 1492
        const val settings_token_stats_col_cost: Int = 1493
        const val settings_token_stats_col_duration: Int = 1494
        const val settings_token_stats_col_input: Int = 1495
        const val settings_token_stats_col_model: Int = 1496
        const val settings_token_stats_col_output: Int = 1497
        const val settings_token_stats_col_provider: Int = 1498
        const val settings_token_stats_col_reasoning_effort: Int = 1499
        const val settings_token_stats_col_reasoning_wire: Int = 1500
        const val settings_token_stats_col_status: Int = 1501
        const val settings_token_stats_col_time: Int = 1502
        const val settings_token_stats_col_ttfb: Int = 1503
        const val settings_token_stats_cost: Int = 1504
        const val settings_token_stats_filter_all_models: Int = 1505
        const val settings_token_stats_filter_all_providers: Int = 1506
        const val settings_token_stats_filter_clear: Int = 1507
        const val settings_token_stats_filter_model: Int = 1508
        const val settings_token_stats_filter_provider: Int = 1509
        const val settings_token_stats_legend_cached: Int = 1510
        const val settings_token_stats_legend_input: Int = 1511
        const val settings_token_stats_legend_output: Int = 1512
        const val settings_token_stats_model: Int = 1513
        const val settings_token_stats_no_data: Int = 1514
        const val settings_token_stats_page: Int = 1515
        const val settings_token_stats_period_30d: Int = 1516
        const val settings_token_stats_period_7d: Int = 1517
        const val settings_token_stats_period_all: Int = 1518
        const val settings_token_stats_period_today: Int = 1519
        const val settings_token_stats_provider: Int = 1520
        const val settings_token_stats_provider_keys: Int = 1521
        const val settings_token_stats_recent_calls: Int = 1522
        const val settings_token_stats_requests: Int = 1523
        const val settings_token_stats_requests_unit: Int = 1524
        const val settings_token_stats_reset: Int = 1525
        const val settings_token_stats_reset_confirm: Int = 1526
        const val settings_token_stats_reset_title: Int = 1527
        const val settings_token_stats_retries: Int = 1528
        const val settings_token_stats_success_rate: Int = 1529
        const val settings_token_stats_title: Int = 1530
        const val settings_token_stats_total: Int = 1531
        const val settings_token_stats_trend: Int = 1532
        const val settings_token_stats_unknown: Int = 1533
        const val settings_tokens_value: Int = 1534
        const val settings_turns_value: Int = 1535
        const val settings_user_guide: Int = 1536
        const val settings_vision_follow_chat: Int = 1537
        const val settings_vision_model: Int = 1538
        const val skill_detail_author: Int = 1991
        const val skill_detail_category: Int = 1994
        const val skill_detail_display_name: Int = 1990
        const val skill_detail_download: Int = 1997
        const val skill_detail_name: Int = 1989
        const val skill_detail_package: Int = 1995
        const val skill_detail_stars: Int = 1992
        const val skill_detail_tags: Int = 1996
        const val skill_detail_version: Int = 1993
        const val skill_install_note: Int = 1998
        const val skill_market_title: Int = 1977
        const val skills_add: Int = 1539
        const val skills_add_from_file: Int = 1540
        const val skills_add_from_file_desc: Int = 1541
        const val skills_add_from_zip: Int = 1542
        const val skills_add_from_zip_desc: Int = 1543
        const val skills_add_manual: Int = 1544
        const val skills_add_manual_desc: Int = 1545
        const val skills_add_sheet_title: Int = 1546
        const val skills_batch_delete_confirm: Int = 1547
        const val skills_batch_delete_title: Int = 1548
        const val skills_delete_confirm_message: Int = 1549
        const val skills_delete_confirm_title: Int = 1550
        const val skills_description: Int = 1551
        const val skills_description_hint: Int = 1552
        const val skills_edit: Int = 1553
        const val skills_editor_basic: Int = 1554
        const val skills_editor_edit: Int = 1555
        const val skills_editor_error_empty_instructions: Int = 1556
        const val skills_editor_error_invalid_name: Int = 1557
        const val skills_editor_error_io: Int = 1558
        const val skills_editor_error_name_conflict: Int = 1559
        const val skills_editor_new: Int = 1560
        const val skills_editor_scope: Int = 1561
        const val skills_empty: Int = 1562
        const val skills_empty_hint: Int = 1563
        const val skills_enable: Int = 1564
        const val skills_import_error_empty_content: Int = 1565
        const val skills_import_error_invalid_archive: Int = 1566
        const val skills_import_error_invalid_name: Int = 1567
        const val skills_import_error_io: Int = 1568
        const val skills_import_error_name_conflict: Int = 1569
        const val skills_import_error_no_skill: Int = 1570
        const val skills_import_error_unsupported_file: Int = 1571
        const val skills_import_failure_line: Int = 1572
        const val skills_import_partial: Int = 1573
        const val skills_import_result_title: Int = 1574
        const val skills_import_running: Int = 1575
        const val skills_import_success: Int = 1576
        const val skills_instructions: Int = 1577
        const val skills_instructions_hint: Int = 1578
        const val skills_name_hint: Int = 1579
        const val skills_no_global_skills: Int = 1580
        const val skills_no_project_skills: Int = 1581
        const val skills_scope_global: Int = 1582
        const val skills_scope_project: Int = 1583
        const val skills_summary: Int = 1584
        const val slash_command_compress_desc: Int = 1585
        const val slash_command_goal_desc: Int = 1586
        const val slash_command_init_desc: Int = 1587
        const val slash_command_usage_desc: Int = 1588
        const val ssh_host_key_changed_title: Int = 1589
        const val ssh_host_key_confirm_title: Int = 1590
        const val ssh_host_key_detail: Int = 1591
        const val ssh_host_key_empty: Int = 1592
        const val ssh_host_key_fingerprint_value: Int = 1593
        const val ssh_host_key_reject: Int = 1594
        const val ssh_host_key_remove: Int = 1595
        const val ssh_host_key_settings_desc: Int = 1596
        const val ssh_host_key_settings_title: Int = 1597
        const val ssh_host_key_tab_fingerprints: Int = 1598
        const val ssh_host_key_tab_keys: Int = 1599
        const val ssh_host_key_trust: Int = 1600
        const val ssh_login_key_add: Int = 1601
        const val ssh_login_key_add_from_file: Int = 1602
        const val ssh_login_key_add_from_file_desc: Int = 1603
        const val ssh_login_key_add_from_paste: Int = 1604
        const val ssh_login_key_add_from_paste_desc: Int = 1605
        const val ssh_login_key_add_method_title: Int = 1606
        const val ssh_login_key_content_label: Int = 1607
        const val ssh_login_key_edit_title: Int = 1608
        const val ssh_login_key_empty: Int = 1609
        const val ssh_login_key_encrypted: Int = 1610
        const val ssh_login_key_fingerprint_label: Int = 1611
        const val ssh_login_key_name_label: Int = 1612
        const val ssh_login_key_passphrase_placeholder: Int = 1613
        const val ssh_login_key_paste_content_placeholder: Int = 1614
        const val ssh_login_key_paste_invalid: Int = 1615
        const val ssh_login_key_paste_name_placeholder: Int = 1616
        const val ssh_login_key_paste_title: Int = 1617
        const val ssh_login_key_toggle_content: Int = 1618
        const val status_connected: Int = 1619
        const val status_connection_failed: Int = 1620
        const val status_disconnected: Int = 1621
        const val status_reconnecting: Int = 1622
        const val storage_breakdown_note: Int = 1623
        const val storage_category_ai_config: Int = 1624
        const val storage_category_apk: Int = 1625
        const val storage_category_caches: Int = 1626
        const val storage_category_chat: Int = 1627
        const val storage_category_checkpoints: Int = 1628
        const val storage_category_container: Int = 1629
        const val storage_category_container_images: Int = 1630
        const val storage_category_logs: Int = 1631
        const val storage_category_other: Int = 1632
        const val storage_category_workspaces: Int = 1633
        const val storage_chat_messages: Int = 1634
        const val storage_clean: Int = 1635
        const val storage_clean_caches: Int = 1636
        const val storage_clean_caches_desc: Int = 1637
        const val storage_clean_confirm_selected: Int = 1638
        const val storage_clean_confirm_title: Int = 1639
        const val storage_clean_logs: Int = 1640
        const val storage_clean_logs_desc: Int = 1641
        const val storage_clean_scope: Int = 1642
        const val storage_clean_selected: Int = 1643
        const val storage_clean_tool_output: Int = 1644
        const val storage_clean_tool_output_desc: Int = 1645
        const val storage_clean_vision: Int = 1646
        const val storage_clean_vision_desc: Int = 1647
        const val storage_device_free: Int = 1648
        const val storage_freed: Int = 1649
        const val storage_logs_ai: Int = 1650
        const val storage_logs_app: Int = 1651
        const val storage_refresh: Int = 1652
        const val storage_scanning: Int = 1653
        const val storage_section_breakdown: Int = 1654
        const val storage_total_title: Int = 1655
        const val storage_untitled_session: Int = 1656
        const val storage_workspace_attachments: Int = 1657
        const val subagent_add: Int = 1658
        const val subagent_all_tools: Int = 1659
        const val subagent_card_changed_files: Int = 1660
        const val subagent_card_collapse: Int = 1661
        const val subagent_card_default_agent: Int = 1662
        const val subagent_card_expand: Int = 1663
        const val subagent_card_open: Int = 1664
        const val subagent_card_status_completed: Int = 1665
        const val subagent_card_status_dispatched: Int = 1666
        const val subagent_card_status_failed: Int = 1667
        const val subagent_card_status_running: Int = 1668
        const val subagent_card_status_stopped: Int = 1669
        const val subagent_card_stop: Int = 1670
        const val subagent_description_hint: Int = 1671
        const val subagent_dispatch_count: Int = 1672
        const val subagent_dispatch_title: Int = 1673
        const val subagent_edit: Int = 1674
        const val subagent_editor_basic: Int = 1675
        const val subagent_editor_clear: Int = 1676
        const val subagent_editor_description: Int = 1677
        const val subagent_editor_edit: Int = 1678
        const val subagent_editor_effort: Int = 1679
        const val subagent_editor_error_empty_prompt: Int = 1680
        const val subagent_editor_error_invalid_name: Int = 1681
        const val subagent_editor_error_io: Int = 1682
        const val subagent_editor_error_name_conflict: Int = 1683
        const val subagent_editor_mode: Int = 1684
        const val subagent_editor_model_group: Int = 1685
        const val subagent_editor_new: Int = 1686
        const val subagent_editor_none: Int = 1687
        const val subagent_editor_scope: Int = 1688
        const val subagent_editor_selected_count: Int = 1689
        const val subagent_editor_tools_allow: Int = 1690
        const val subagent_editor_tools_allow_hint: Int = 1691
        const val subagent_editor_tools_deny: Int = 1692
        const val subagent_editor_tools_deny_hint: Int = 1693
        const val subagent_editor_tools_search: Int = 1694
        const val subagent_enable: Int = 1695
        const val subagent_enable_hint: Int = 1696
        const val subagent_inherit_parent: Int = 1697
        const val subagent_inject: Int = 1698
        const val subagent_inject_base: Int = 1699
        const val subagent_inject_base_hint: Int = 1700
        const val subagent_inject_main_rules: Int = 1701
        const val subagent_inject_main_rules_hint: Int = 1702
        const val subagent_inject_memory: Int = 1703
        const val subagent_inject_memory_hint: Int = 1704
        const val subagent_inject_none: Int = 1705
        const val subagent_inject_project_rules: Int = 1706
        const val subagent_inject_project_rules_hint: Int = 1707
        const val subagent_inject_skills: Int = 1708
        const val subagent_inject_skills_hint: Int = 1709
        const val subagent_model: Int = 1710
        const val subagent_name_hint: Int = 1711
        const val subagent_no_models: Int = 1712
        const val subagent_prompt: Int = 1713
        const val subagent_prompt_hint: Int = 1714
        const val subagent_scope_global: Int = 1715
        const val subagent_scope_project: Int = 1716
        const val subagent_source_file: Int = 1717
        const val subagent_tab_sessions: Int = 1718
        const val subagent_tools: Int = 1719
        const val subagent_tools_allow: Int = 1720
        const val subagent_tools_deny: Int = 1721
        const val subagents_batch_delete_confirm: Int = 1722
        const val subagents_batch_delete_title: Int = 1723
        const val subagents_delete_confirm_message: Int = 1724
        const val subagents_delete_confirm_title: Int = 1725
        const val subagents_empty: Int = 1726
        const val subagents_empty_hint: Int = 1727
        const val subagents_no_global: Int = 1728
        const val subagents_no_project: Int = 1729
        const val sync_batch_size_desc: Int = 1730
        const val sync_clear_index: Int = 1731
        const val sync_connect_and_sync: Int = 1732
        const val sync_disconnect: Int = 1733
        const val sync_download_all: Int = 1734
        const val sync_follow_gitignore: Int = 1735
        const val sync_gitignore_desc: Int = 1736
        const val sync_max_batch_count: Int = 1737
        const val sync_max_batch_size: Int = 1738
        const val sync_path_mapping: Int = 1739
        const val sync_save_settings: Int = 1740
        const val sync_saved: Int = 1741
        const val sync_settings_title: Int = 1742
        const val sync_unknown_connection: Int = 1743
        const val sync_upload_changes: Int = 1744
        const val sync_upload_full: Int = 1745
        const val terminal_cleaning_old_container: Int = 1746
        const val terminal_close_tab: Int = 1747
        const val terminal_cursor_bar: Int = 1748
        const val terminal_cursor_block: Int = 1749
        const val terminal_cursor_style_title: Int = 1750
        const val terminal_cursor_underline: Int = 1751
        const val terminal_extracting_env: Int = 1752
        const val terminal_font_builtin: Int = 1753
        const val terminal_font_default: Int = 1754
        const val terminal_font_delete: Int = 1755
        const val terminal_font_import: Int = 1756
        const val terminal_font_import_failed: Int = 1757
        const val terminal_font_size_title: Int = 1758
        const val terminal_font_title: Int = 1759
        const val terminal_installing_packages: Int = 1760
        const val terminal_no_open_tabs: Int = 1761
        const val terminal_prepare_error_unknown: Int = 1762
        const val terminal_preparing_env_failed: Int = 1763
        const val terminal_preparing_env_first_run: Int = 1764
        const val terminal_preview_text: Int = 1765
        const val terminal_retry: Int = 1766
        const val terminal_settings_title: Int = 1767
        const val terminal_start_failed: Int = 1768
        const val terminal_theme_dracula: Int = 1769
        const val terminal_theme_follow_app: Int = 1770
        const val terminal_theme_github_dark: Int = 1771
        const val terminal_theme_github_light: Int = 1772
        const val terminal_theme_monokai: Int = 1773
        const val terminal_theme_one_dark: Int = 1774
        const val terminal_theme_termius_dark: Int = 1775
        const val terminal_theme_title: Int = 1776
        const val terminal_title: Int = 1777
        const val theme_auto: Int = 1778
        const val theme_dark: Int = 1779
        const val theme_dynamic_color: Int = 1780
        const val theme_dynamic_color_desc: Int = 1781
        const val theme_dynamic_color_unsupported: Int = 1782
        const val theme_light: Int = 1783
        const val theme_mode_section: Int = 1784
        const val theme_preset_coffee: Int = 1785
        const val theme_preset_default: Int = 1786
        const val theme_preset_forest: Int = 1787
        const val theme_preset_graphite: Int = 1788
        const val theme_preset_section: Int = 1789
        const val title_no_models: Int = 1790
        const val todo_completed: Int = 1791
        const val todo_dashboard_all_done: Int = 1792
        const val todo_dashboard_progress: Int = 1793
        const val todo_dashboard_title: Int = 1794
        const val todo_in_progress: Int = 1795
        const val token_stats_hour_suffix: Int = 1796
        const val tool_browser_condition_met: Int = 1797
        const val tool_browser_screenshot_failed: Int = 1798
        const val tool_browser_wait_timeout: Int = 1799
        const val tool_changed_files_summary: Int = 1800
        const val tool_command_rule_denied: Int = 1801
        const val tool_command_rule_denied_detail: Int = 1802
        const val tool_command_unanalyzable_reason: Int = 1803
        const val tool_expand_remaining: Int = 1804
        const val tool_instruction: Int = 1805
        const val tool_mcp_invalid_name: Int = 1806
        const val tool_mcp_stdio_added: Int = 1807
        const val tool_memory_saved: Int = 1808
        const val tool_output_omitted: Int = 1809
        const val tool_output_saved_hint: Int = 1810
        const val tool_output_unsaved_hint: Int = 1811
        const val tool_permission_rule_denied: Int = 1812
        const val tool_plan_mode_denied: Int = 1813
        const val tool_result: Int = 1814
        const val tool_shizuku_confirmation_reason: Int = 1815
        const val tool_subagent_message_delivered: Int = 1816
        const val tool_subagent_plan_readonly_warning: Int = 1817
        const val tool_subagent_provider_unresolved: Int = 1818
        const val tool_subagent_tier_fallback: Int = 1819
        const val tool_subagent_tier_pinned: Int = 1820
        const val tool_subagent_tier_unavailable: Int = 1821
        const val tool_vision_default_multiple: Int = 1822
        const val tool_vision_default_single: Int = 1823
        const val tool_vision_empty_response: Int = 1824
        const val tool_vision_focus: Int = 1825
        const val tool_vision_images_attached: Int = 1826
        const val update_channel_latest: Int = 1827
        const val update_channel_latest_desc: Int = 1828
        const val update_channel_stable: Int = 1829
        const val update_channel_stable_desc: Int = 1830
        const val vision_no_models: Int = 1831
        const val web_search_results_count: Int = 1832
        const val web_search_title: Int = 1833
        const val web_search_warning: Int = 1834
        const val workbench_split_handle: Int = 1835
        const val workspace_add_external: Int = 1836
        const val workspace_confirm: Int = 1837
        const val workspace_delete: Int = 1838
        const val workspace_delete_confirm: Int = 1839
        const val workspace_empty_hint: Int = 1840
        const val workspace_external_added: Int = 1841
        const val workspace_external_exists: Int = 1842
        const val workspace_external_invalid: Int = 1843
        const val workspace_external_remove_confirm: Int = 1844
        const val workspace_external_remove_confirm_sessions: Int = 1845
        const val workspace_external_unavailable_hint: Int = 1846
        const val workspace_external_unresolvable: Int = 1847
        const val workspace_external_unsupported_path: Int = 1848
        const val workspace_external_unwritable: Int = 1849
        const val workspace_external_warning_continue: Int = 1850
        const val workspace_external_warning_dont_show_again: Int = 1851
        const val workspace_external_warning_message: Int = 1852
        const val workspace_external_warning_title: Int = 1853
        const val workspace_name_exists: Int = 1854
        const val workspace_name_hint: Int = 1855
        const val workspace_new_internal: Int = 1856
        const val workspace_new_workspace: Int = 1857
        const val workspace_open: Int = 1858
        const val workspace_remote_default_create_failed: Int = 1859
        const val workspace_remote_root_create_failed: Int = 1860
        const val workspace_select: Int = 1861
        const val workspace_switch: Int = 1862
        const val workspace_switch_confirm: Int = 1863
        const val workspace_unavailable_current: Int = 1864
        const val workspace_unavailable_fallback: Int = 1865
        const val chat_upload_video: Int = 1968
        const val chat_upload_package: Int = 1969
        const val about_upstream: Int = 1974
        const val about_releases: Int = 1975
        const val about_feedback: Int = 1976
    }
}

private val ID_TO_KEY: Map<Int, String> = mapOf(
    1 to "about_auto_check_update",
    2 to "about_auto_check_update_desc",
    3 to "about_check_failed",
    4 to "about_check_update",
    5 to "about_checking_update",
    6 to "about_description",
    7 to "about_download",
    8 to "about_github_repo",
    9 to "about_later",
    10 to "about_license",
    11 to "about_network_error",
    12 to "about_new_version_found",
    13 to "about_no_changelog",
    14 to "about_ok",
    1928 to "about_qq_group",
    1929 to "about_qq_group_copied",
    15 to "about_parse_version_failed",
    16 to "about_up_to_date",
    17 to "about_up_to_date_detail",
    18 to "about_update_apk_ready",
    19 to "about_update_background",
    20 to "about_update_browser",
    21 to "about_update_channel",
    22 to "about_update_download_failed",
    23 to "about_update_download_failed_hint",
    24 to "about_update_download_install",
    25 to "about_update_download_size",
    26 to "about_update_downloading",
    27 to "about_update_install",
    28 to "about_update_install_failed",
    29 to "about_update_mirror",
    30 to "about_update_mirror_default",
    31 to "about_update_mirror_desc",
    32 to "about_update_mirror_invalid",
    33 to "about_update_mirror_preset_cdn",
    34 to "about_update_mirror_preset_direct",
    35 to "about_update_mirror_preset_v6",
    36 to "about_update_need_permission",
    37 to "about_update_retry",
    38 to "about_update_verify_not_apk",
    39 to "about_update_verify_not_newer",
    40 to "about_update_verify_package",
    41 to "about_update_verify_signature",
    42 to "about_version",
    43 to "agent_compaction_failed",
    44 to "agent_complete_notification_background_body",
    45 to "agent_complete_notification_background_multi_body",
    46 to "agent_complete_notification_body",
    47 to "agent_complete_notification_title",
    48 to "agent_context_no_compaction",
    49 to "agent_input_budget_exceeded",
    50 to "agent_model_not_selected",
    51 to "agent_stop_context_exceeded",
    52 to "agent_stop_refusal",
    53 to "agent_stopped_by_user",
    54 to "agent_tool_executing",
    55 to "agreement_accept",
    56 to "agreement_decline",
    57 to "agreement_notice_conjunction",
    58 to "agreement_notice_prefix",
    59 to "agreement_notice_suffix",
    60 to "agreement_privacy",
    61 to "agreement_privacy_link",
    62 to "agreement_privacy_url",
    63 to "agreement_terms",
    64 to "agreement_terms_link",
    65 to "agreement_terms_url",
    66 to "agreement_title",
    67 to "app_name",
    68 to "ask_confirm",
    69 to "ask_confirm_title",
    70 to "ask_custom_answer",
    71 to "ask_input_hint",
    72 to "ask_supplement",
    73 to "backup_actions",
    74 to "backup_data_app_settings",
    75 to "backup_data_chat_history",
    76 to "backup_data_mcp",
    77 to "backup_data_permissions",
    78 to "backup_data_remote",
    79 to "backup_data_workspace",
    80 to "backup_export_btn",
    81 to "backup_export_failed",
    82 to "backup_export_subtitle",
    83 to "backup_export_title",
    84 to "backup_exported",
    85 to "backup_import_btn",
    86 to "backup_import_done",
    87 to "backup_import_failed",
    88 to "backup_import_subtitle",
    89 to "backup_import_title",
    90 to "backup_net_access_key",
    91 to "backup_net_add_target",
    92 to "backup_net_base_path",
    93 to "backup_net_base_path_hint",
    94 to "backup_net_bucket",
    95 to "backup_net_delete_failed",
    96 to "backup_net_delete_target_confirm",
    97 to "backup_net_edit_target",
    98 to "backup_net_endpoint",
    99 to "backup_net_endpoint_hint",
    100 to "backup_net_list_failed",
    101 to "backup_net_loading",
    102 to "backup_net_name",
    103 to "backup_net_no_target",
    104 to "backup_net_no_target_hint",
    105 to "backup_net_password",
    106 to "backup_net_path_style",
    107 to "backup_net_region",
    108 to "backup_net_remote_delete",
    109 to "backup_net_remote_delete_confirm",
    110 to "backup_net_remote_empty",
    111 to "backup_net_remote_list_title",
    112 to "backup_net_remote_restore",
    113 to "backup_net_restore",
    114 to "backup_net_restore_subtitle",
    115 to "backup_net_secret_key",
    116 to "backup_net_section",
    117 to "backup_net_session_token",
    118 to "backup_net_targets",
    119 to "backup_net_test_connection",
    120 to "backup_net_test_failed",
    121 to "backup_net_test_success",
    122 to "backup_net_testing",
    123 to "backup_net_type_s3",
    124 to "backup_net_type_webdav",
    125 to "backup_net_upload",
    126 to "backup_net_upload_done",
    127 to "backup_net_upload_failed",
    128 to "backup_net_upload_password_hint",
    129 to "backup_net_upload_password_required",
    130 to "backup_net_upload_short",
    131 to "backup_net_upload_subtitle",
    132 to "backup_net_uploading",
    133 to "backup_net_username",
    134 to "backup_operation_failed",
    135 to "backup_password_hint",
    136 to "backup_password_input",
    137 to "backup_password_label",
    138 to "backup_password_optional_hint",
    139 to "backup_processing",
    140 to "backup_processing_data",
    141 to "backup_read_failed",
    142 to "backup_restore_workspaces_confirm",
    143 to "backup_restore_workspaces_files",
    144 to "backup_restore_workspaces_select_all",
    145 to "backup_restore_workspaces_title",
    146 to "backup_restore_workspaces_warning",
    147 to "backup_restored_data",
    148 to "backup_select_data",
    149 to "backup_set_password",
    150 to "backup_settings_covered",
    151 to "backup_stat_chat_messages",
    152 to "backup_stat_chat_sessions",
    153 to "backup_stat_mcp_servers",
    154 to "backup_stat_permission_rules",
    155 to "backup_stat_providers",
    156 to "backup_stat_remote_connections",
    157 to "backup_stat_remote_mounts",
    158 to "backup_stat_todo_items",
    159 to "backup_stat_workspace_files",
    160 to "backup_tab_local",
    161 to "backup_workspace_git_hint",
    162 to "backup_write_failed",
    163 to "backup_wrong_password",
    164 to "browser_address_hint",
    165 to "browser_back",
    166 to "browser_close_tab",
    167 to "browser_devtools",
    168 to "browser_devtools_desc",
    169 to "browser_devtools_title",
    170 to "browser_dialog_alert_title",
    171 to "browser_dialog_confirm_title",
    172 to "browser_dialog_prompt_title",
    173 to "browser_empty_click_hint",
    174 to "browser_empty_hint",
    175 to "browser_forward",
    176 to "browser_menu",
    177 to "browser_menu_general_section",
    178 to "browser_menu_title",
    179 to "browser_new_tab",
    180 to "browser_night_mode_desc",
    181 to "browser_night_mode_off",
    182 to "browser_night_mode_on",
    183 to "browser_night_mode_title",
    184 to "browser_not_ready",
    185 to "browser_reload",
    186 to "browser_tab_default_title",
    187 to "browser_tabs_count",
    188 to "browser_tabs_manage",
    189 to "browser_title",
    190 to "browser_ua_android_phone",
    191 to "browser_ua_android_phone_desc",
    192 to "browser_ua_android_tablet",
    193 to "browser_ua_android_tablet_desc",
    194 to "browser_ua_applied_toast",
    195 to "browser_ua_custom",
    196 to "browser_ua_custom_desc",
    197 to "browser_ua_custom_dialog_title",
    198 to "browser_ua_custom_input_hint",
    199 to "browser_ua_default",
    200 to "browser_ua_default_desc",
    201 to "browser_ua_desc",
    202 to "browser_ua_desktop_pc",
    203 to "browser_ua_desktop_pc_desc",
    204 to "browser_ua_ipad",
    205 to "browser_ua_ipad_desc",
    206 to "browser_ua_iphone",
    207 to "browser_ua_iphone_desc",
    208 to "browser_ua_section",
    209 to "chat_action_copy",
    210 to "chat_action_delete",
    211 to "chat_action_edit",
    212 to "chat_action_fork",
    213 to "chat_add_attachment",
    214 to "chat_approve_and_implement",
    215 to "chat_attachment_max",
    216 to "chat_attachment_open",
    217 to "chat_attachment_prefix",
    218 to "chat_attachment_preview",
    219 to "chat_attachment_uploaded",
    220 to "chat_attachment_uploaded_partial",
    221 to "chat_attachment_uploading",
    222 to "chat_batch_delete_confirm",
    223 to "chat_batch_delete_title",
    224 to "chat_batch_select",
    225 to "chat_bg_command_done",
    226 to "chat_bg_commands_done",
    227 to "chat_bg_commands_partial_failed",
    228 to "chat_cache_hit_rate",
    229 to "chat_camera_file_failed",
    230 to "chat_compaction_failed",
    231 to "chat_compressing_context",
    232 to "chat_connecting_remote",
    233 to "chat_context_compressed",
    234 to "chat_context_usage",
    235 to "chat_context_usage_estimated",
    236 to "chat_continue_feedback",
    237 to "chat_copied",
    238 to "chat_copy",
    239 to "chat_delete_session",
    240 to "chat_delete_session_confirm",
    241 to "chat_edit_dialog_hint",
    242 to "chat_edit_dialog_title",
    243 to "chat_error_title",
    244 to "chat_export_session",
    245 to "chat_export_session_done",
    246 to "chat_export_session_failed",
    247 to "chat_fork_title",
    248 to "chat_goal_clear",
    249 to "chat_goal_label",
    250 to "chat_goal_pause",
    251 to "chat_goal_resume",
    252 to "chat_goal_status_active",
    253 to "chat_goal_status_blocked",
    254 to "chat_goal_status_completed",
    255 to "chat_goal_status_exhausted",
    256 to "chat_goal_status_paused",
    257 to "chat_history",
    258 to "chat_image_too_large",
    259 to "chat_input_expand",
    260 to "chat_input_fullscreen_title",
    261 to "chat_input_hint",
    262 to "chat_input_placeholder",
    263 to "chat_key_switched",
    264 to "chat_mention_empty",
    265 to "chat_mention_error",
    266 to "chat_mention_loading",
    267 to "chat_mention_unavailable",
    268 to "chat_more_options",
    269 to "chat_new_session",
    270 to "chat_new_session_btn",
    271 to "chat_no_model_selected",
    272 to "chat_no_models_hint",
    273 to "chat_no_remote_connection",
    274 to "chat_no_sessions_hint",
    275 to "chat_open_apk_permission",
    276 to "chat_open_browser",
    277 to "chat_open_file_missing",
    278 to "chat_open_file_no_app",
    279 to "chat_open_file_unshareable",
    280 to "chat_open_git",
    281 to "chat_open_sidebar",
    282 to "chat_open_terminal",
    283 to "chat_perm_always_allow",
    284 to "chat_perm_always_prefix",
    285 to "chat_perm_always_tool_desc",
    286 to "chat_perm_deny",
    287 to "chat_perm_session_label",
    288 to "chat_perm_single_use_desc",
    289 to "chat_pin_session",
    290 to "chat_placeholder",
    291 to "chat_plan_completed",
    292 to "chat_queue_drag",
    293 to "chat_queue_edit",
    294 to "chat_queue_hint",
    295 to "chat_queue_interject",
    296 to "chat_queue_remove",
    297 to "chat_queue_title",
    298 to "chat_read_file_failed",
    299 to "chat_reasoning_cannot_disable",
    300 to "chat_reasoning_default_tag",
    301 to "chat_reasoning_effective",
    302 to "chat_reasoning_effective_tag",
    303 to "chat_reasoning_effort",
    304 to "chat_reasoning_effort_high",
    305 to "chat_reasoning_effort_low",
    306 to "chat_reasoning_effort_max",
    307 to "chat_reasoning_effort_medium",
    308 to "chat_reasoning_effort_minimal",
    309 to "chat_reasoning_effort_none",
    310 to "chat_reasoning_effort_xhigh",
    311 to "chat_reasoning_endpoint_weak",
    312 to "chat_reasoning_off_short",
    313 to "chat_reasoning_toggle",
    314 to "chat_reasoning_unknown_hint",
    315 to "chat_remote_connect_failed",
    316 to "chat_remove_attachment",
    317 to "chat_rename_session",
    318 to "chat_retrying",
    319 to "chat_search_clear",
    320 to "chat_search_hint",
    321 to "chat_search_no_result",
    322 to "chat_select_workspace_first",
    323 to "chat_send",
    324 to "chat_session_name",
    325 to "chat_sessions_selected_count",
    326 to "chat_settings",
    327 to "chat_ssh_connected",
    328 to "chat_ssh_connecting",
    329 to "chat_ssh_disconnected",
    330 to "chat_ssh_failed",
    331 to "chat_status_asking_user",
    332 to "chat_status_browsing_web",
    333 to "chat_status_calling_tool",
    334 to "chat_status_editing_file",
    335 to "chat_status_generating",
    336 to "chat_status_generating_image",
    337 to "chat_status_reading_file",
    338 to "chat_status_reading_memory",
    339 to "chat_status_running_command",
    340 to "chat_status_searching_web",
    341 to "chat_status_starting_subagent",
    342 to "chat_status_thinking",
    343 to "chat_status_updating_todo",
    344 to "chat_status_viewing_image",
    345 to "chat_stop",
    346 to "chat_take_photo",
    347 to "chat_task_duration",
    348 to "chat_thinking_done",
    349 to "chat_thinking_done_time",
    350 to "chat_thinking_process",
    351 to "chat_thinking_running_time",
    352 to "chat_tool_calls_count",
    353 to "chat_tool_failed",
    354 to "chat_tool_running",
    355 to "chat_tool_succeeded",
    356 to "chat_turn_completed",
    357 to "chat_turn_completed_plain",
    358 to "chat_turn_running",
    359 to "chat_unpin_session",
    360 to "chat_unsupported_image_type",
    361 to "chat_upload_failed",
    362 to "chat_upload_file",
    363 to "chat_upload_image",
    364 to "chat_uploaded_to",
    365 to "chat_workspace_unavailable",
    366 to "checkpoint_restore_code",
    367 to "checkpoint_restore_code_and_conversation",
    368 to "checkpoint_restore_code_and_conversation_desc",
    369 to "checkpoint_restore_code_desc",
    370 to "checkpoint_restore_conversation",
    371 to "checkpoint_restore_conversation_desc",
    372 to "checkpoint_rewind_title",
    373 to "checkpoint_skipped_multiple",
    374 to "checkpoint_skipped_single",
    375 to "checkpoint_target_prompt",
    376 to "common_add",
    377 to "common_ai_providers",
    378 to "common_allow",
    379 to "common_back",
    380 to "common_batch_delete",
    381 to "common_batch_deleting",
    382 to "common_batch_disable",
    383 to "common_batch_enable",
    384 to "common_batch_reconnect",
    385 to "common_cancel",
    386 to "common_clear",
    387 to "common_close",
    388 to "common_collapse",
    389 to "common_collapse_action",
    390 to "common_confirm",
    391 to "common_copied_with_title",
    392 to "common_copy",
    393 to "common_copy_success",
    394 to "common_create",
    395 to "common_cut",
    396 to "common_delete",
    397 to "common_deselect_all",
    398 to "common_disabled",
    399 to "common_done",
    400 to "common_edit",
    401 to "common_enabled",
    402 to "common_error",
    403 to "common_error_unknown",
    404 to "common_expand",
    405 to "common_file",
    406 to "common_go_to_settings",
    407 to "common_got_it",
    408 to "common_hide",
    409 to "common_image_load_failed",
    410 to "common_image_preview",
    411 to "common_label_with_api",
    412 to "common_local",
    413 to "common_model",
    414 to "common_more",
    415 to "common_name",
    416 to "common_new_tab",
    417 to "common_next",
    418 to "common_open_link_failed",
    419 to "common_other",
    420 to "common_overwrite",
    421 to "common_paste",
    422 to "common_prev",
    423 to "common_rename",
    424 to "common_reset",
    425 to "common_retry",
    426 to "common_save",
    427 to "common_scroll_to_bottom",
    428 to "common_select",
    429 to "common_select_all",
    430 to "common_select_mode",
    431 to "common_selected_count",
    432 to "common_show",
    433 to "common_switch",
    434 to "common_tool",
    435 to "common_tool_missing_argument",
    436 to "common_username",
    437 to "common_workspace",
    438 to "compaction_no_models",
    439 to "container_add_arg",
    440 to "container_add_binding",
    441 to "container_add_env_var",
    442 to "container_add_image",
    443 to "container_announcement_got_it",
    444 to "container_announcement_title",
    445 to "container_arg_value",
    446 to "container_builtin_badge",
    447 to "container_builtin_restorable",
    448 to "container_channel_deleted",
    449 to "container_custom_badge",
    450 to "container_custom_image",
    451 to "container_default_badge",
    452 to "container_default_desc",
    453 to "container_delete_config",
    454 to "container_delete_confirm",
    455 to "container_download_architecture",
    456 to "container_download_batch_delete_confirm",
    457 to "container_download_batch_delete_title",
    458 to "container_download_cancel",
    459 to "container_download_checksum_failed",
    460 to "container_download_current_source",
    461 to "container_download_current_source_title",
    462 to "container_download_delete_confirm",
    463 to "container_download_delete_title",
    464 to "container_download_done",
    465 to "container_download_eol_ended",
    466 to "container_download_eol_extended",
    467 to "container_download_eol_extended_generic",
    468 to "container_download_eol_fully_ends",
    469 to "container_download_eol_soon",
    470 to "container_download_eol_support_ends",
    471 to "container_download_failed",
    472 to "container_download_image",
    473 to "container_download_import",
    474 to "container_download_imported_toast",
    475 to "container_download_list_empty",
    476 to "container_download_newer_available",
    477 to "container_download_redownload",
    478 to "container_download_size",
    479 to "container_download_source_unavailable",
    480 to "container_download_start",
    481 to "container_edit_image",
    482 to "container_empty",
    483 to "container_empty_desc",
    484 to "container_env_name",
    485 to "container_env_value",
    486 to "container_env_vars",
    487 to "container_extra_bindings",
    488 to "container_extra_proot_args",
    489 to "container_file_selected",
    490 to "container_importing",
    491 to "container_local_image",
    492 to "container_mount_container",
    493 to "container_mount_local",
    494 to "container_no_args",
    495 to "container_no_bindings",
    496 to "container_no_env_vars",
    497 to "container_no_sftp_channel",
    498 to "container_not_ready_hint",
    499 to "container_remote_ssh",
    500 to "container_remote_workspace_path",
    501 to "container_reset",
    502 to "container_reset_confirm",
    503 to "container_reset_title",
    504 to "container_resetting_message",
    505 to "container_resetting_title",
    506 to "container_restore_builtin",
    507 to "container_rootfs_will_be_cleared",
    508 to "container_select_image_file",
    509 to "container_select_ssh_channel",
    510 to "container_set_as_default",
    511 to "container_shell_desc",
    512 to "container_shell_path",
    513 to "container_sort_long_press",
    514 to "container_ssh_badge",
    515 to "container_ssh_channel",
    516 to "container_switch_confirm",
    517 to "container_switch_image",
    518 to "crash_banner_title",
    519 to "crash_copied",
    520 to "crash_copy",
    521 to "crash_exit",
    522 to "crash_field_device",
    523 to "crash_field_memory",
    524 to "crash_field_screen",
    525 to "crash_field_thread",
    526 to "crash_field_time",
    527 to "crash_field_version",
    528 to "crash_field_workspace_mode",
    529 to "crash_log_hint",
    530 to "crash_message",
    531 to "crash_mode_local",
    532 to "crash_mode_remote",
    533 to "crash_mode_unknown",
    534 to "crash_report_device",
    535 to "crash_report_log",
    536 to "crash_report_memory",
    537 to "crash_report_mode",
    538 to "crash_report_screen",
    539 to "crash_report_thread",
    540 to "crash_report_time",
    541 to "crash_report_title",
    542 to "crash_report_version",
    543 to "crash_restart",
    544 to "crash_screen_chat",
    545 to "crash_screen_git",
    546 to "crash_screen_settings",
    547 to "crash_screen_terminal",
    548 to "crash_screen_unknown",
    549 to "crash_section_environment",
    550 to "crash_section_stack",
    551 to "crash_title",
    552 to "credential_add",
    553 to "credential_add_github_desc",
    554 to "credential_add_github_title",
    555 to "credential_add_manual",
    556 to "credential_add_manual_desc",
    557 to "credential_add_manual_subtitle",
    558 to "credential_add_manual_title",
    559 to "credential_add_recommend",
    560 to "credential_add_sheet_subtitle",
    561 to "credential_add_sheet_title",
    562 to "credential_auto_inject_enable",
    563 to "credential_auto_inject_hint",
    564 to "credential_auto_inject_prompt_body",
    565 to "credential_auto_inject_prompt_title",
    566 to "credential_auto_inject_title",
    567 to "credential_device_authorize_and_open",
    568 to "credential_device_bad_client",
    569 to "credential_device_card_label",
    570 to "credential_device_code_copied",
    571 to "credential_device_code_copied_opening",
    572 to "credential_device_copy_code",
    573 to "credential_device_denied",
    574 to "credential_device_expired",
    575 to "credential_device_failed",
    576 to "credential_device_failed_title",
    577 to "credential_device_flow_disabled",
    578 to "credential_device_install_later",
    579 to "credential_device_install_now",
    580 to "credential_device_install_required",
    581 to "credential_device_instruction",
    582 to "credential_device_open_browser",
    583 to "credential_device_requesting",
    584 to "credential_device_step_1",
    585 to "credential_device_step_2",
    586 to "credential_device_subtitle",
    587 to "credential_device_success",
    588 to "credential_device_success_title",
    589 to "credential_device_tap_to_copy",
    590 to "credential_device_title",
    591 to "credential_device_unknown_error",
    592 to "credential_device_waiting",
    593 to "credential_device_waiting_desc",
    594 to "credential_edit",
    595 to "credential_empty_desc",
    596 to "credential_empty_hint",
    597 to "credential_empty_title",
    598 to "credential_host",
    599 to "credential_prompt_desc",
    600 to "credential_prompt_title",
    601 to "credential_quick_login",
    602 to "credential_quick_login_desc",
    603 to "credential_quick_login_github_desc",
    604 to "credential_quick_login_title",
    605 to "credential_remote_host",
    606 to "credential_save_and_retry",
    607 to "credential_toast_deleted",
    608 to "credential_toast_identity_saved",
    609 to "credential_toast_save_failed",
    610 to "credential_toast_saved",
    611 to "credential_token",
    612 to "dashboard_exec_failed",
    613 to "dashboard_fetch_failed_retry",
    614 to "dashboard_fetching",
    615 to "dashboard_query_failed",
    616 to "dashboard_tap_to_query",
    617 to "diff_binary_file",
    618 to "diff_file_too_large",
    619 to "diff_file_too_large_desc",
    620 to "diff_no_changes",
    621 to "drawer_collapse_subagents",
    622 to "drawer_expand_subagents",
    623 to "drawer_tab_files",
    624 to "editor_binary_unsupported",
    625 to "editor_cursor_left",
    626 to "editor_cursor_right",
    627 to "editor_discard",
    628 to "editor_dont_save",
    629 to "editor_file_too_large",
    630 to "editor_font_size",
    631 to "editor_font_size_value",
    632 to "editor_indent",
    633 to "editor_load_failed",
    634 to "editor_md_preview",
    635 to "editor_md_show_source",
    636 to "editor_redo",
    637 to "editor_save_and_exit",
    638 to "editor_save_and_switch",
    639 to "editor_save_conflict_cancel",
    640 to "editor_save_conflict_diff",
    641 to "editor_save_conflict_discard",
    642 to "editor_save_conflict_discarded",
    643 to "editor_save_conflict_message",
    644 to "editor_save_conflict_overwrite",
    645 to "editor_save_conflict_title",
    646 to "editor_save_failed",
    647 to "editor_save_success",
    648 to "editor_settings",
    649 to "editor_show_indent_guide",
    650 to "editor_show_whitespace",
    651 to "editor_show_wrap_arrow",
    652 to "editor_undo",
    653 to "editor_unsaved_message",
    654 to "editor_unsaved_title",
    655 to "editor_word_wrap",
    656 to "file_browser_clipboard_copy",
    657 to "file_browser_clipboard_cut",
    658 to "file_browser_collapse",
    659 to "file_browser_create_failed",
    660 to "file_browser_delete_failed",
    661 to "file_browser_delete_file_confirm",
    662 to "file_browser_delete_folder_confirm",
    663 to "file_browser_empty",
    664 to "file_browser_error",
    665 to "file_browser_expand",
    666 to "file_browser_name_label",
    667 to "file_browser_new_file",
    668 to "file_browser_new_folder",
    669 to "file_browser_paste_conflict_message",
    670 to "file_browser_paste_conflict_title",
    671 to "file_browser_paste_here",
    672 to "file_browser_refresh",
    673 to "file_browser_rename_failed",
    674 to "file_browser_up",
    675 to "ftp_address_copied",
    676 to "ftp_allow_anonymous",
    677 to "ftp_anonymous_confirm_message",
    678 to "ftp_anonymous_confirm_title",
    679 to "ftp_anonymous_desc",
    680 to "ftp_auto_start",
    681 to "ftp_auto_start_desc",
    682 to "ftp_config_saved",
    683 to "ftp_config_title",
    684 to "ftp_copy_address",
    685 to "ftp_error",
    686 to "ftp_listen_port",
    687 to "ftp_login_password",
    688 to "ftp_login_username",
    689 to "ftp_not_running",
    690 to "ftp_password_required",
    691 to "ftp_reserved_username",
    692 to "ftp_running",
    693 to "ftp_save_config",
    694 to "ftp_usage_item_1",
    695 to "ftp_usage_item_2",
    696 to "ftp_usage_item_3",
    697 to "ftp_usage_title",
    698 to "ftp_user_cache_cleanup_failed",
    699 to "git_abort_merge_confirm",
    700 to "git_action_abort_merge",
    701 to "git_action_clone",
    702 to "git_action_commit",
    703 to "git_action_copy_path",
    704 to "git_action_create_branch",
    705 to "git_action_create_tag",
    706 to "git_action_delete_branch",
    707 to "git_action_delete_dir",
    708 to "git_action_delete_file",
    709 to "git_action_delete_remote_branch",
    710 to "git_action_delete_tag",
    711 to "git_action_init",
    712 to "git_action_merge",
    713 to "git_action_rename_branch",
    714 to "git_action_restore_file",
    715 to "git_action_revert",
    716 to "git_action_revert_all",
    717 to "git_action_stage_all",
    718 to "git_action_stash",
    719 to "git_action_unstage_all",
    720 to "git_action_view_diff",
    721 to "git_add_to_gitignore",
    722 to "git_ahead_count",
    723 to "git_base_branch",
    724 to "git_behind_count",
    725 to "git_branch_name",
    726 to "git_branch_synced",
    727 to "git_checked_out",
    728 to "git_clean",
    729 to "git_clone_desc",
    730 to "git_clone_dialog_title",
    731 to "git_clone_repo",
    732 to "git_clone_url_hint",
    733 to "git_clone_url_label",
    734 to "git_cloning_hint",
    735 to "git_commit_ai_generate",
    736 to "git_commit_amend",
    737 to "git_commit_changes",
    738 to "git_commit_count",
    739 to "git_commit_dialog_title",
    740 to "git_commit_generating",
    741 to "git_commit_message",
    742 to "git_commit_needs_identity",
    743 to "git_computing_diff",
    744 to "git_conflicted_count",
    745 to "git_copy_full_hash",
    746 to "git_copy_short_hash",
    747 to "git_create_and_switch",
    748 to "git_create_branch_from_commit",
    749 to "git_create_tag_at_commit",
    750 to "git_create_tag_desc",
    751 to "git_create_tag_title",
    752 to "git_create_tracking_branch",
    753 to "git_credentials_and_identity",
    754 to "git_credentials_count",
    755 to "git_current_branch",
    756 to "git_delete_branch",
    757 to "git_delete_local_branch_confirm",
    758 to "git_delete_remote_branch",
    759 to "git_delete_remote_branch_confirm",
    760 to "git_delete_tag",
    761 to "git_delete_tag_confirm",
    762 to "git_delete_untracked_confirm",
    763 to "git_detached_head_label",
    764 to "git_detached_head_warning",
    765 to "git_expanded",
    766 to "git_files_changed",
    767 to "git_has_changes",
    768 to "git_identity_desc",
    769 to "git_identity_title",
    770 to "git_ignore_dir",
    771 to "git_ignore_extension",
    772 to "git_ignore_file",
    773 to "git_init_desc_primary",
    774 to "git_init_desc_secondary",
    775 to "git_init_repo",
    776 to "git_latest_commit",
    777 to "git_latest_date",
    778 to "git_load_more_commits",
    779 to "git_loading_branches",
    780 to "git_loading_files",
    781 to "git_merge_commit",
    782 to "git_merge_confirm",
    783 to "git_merge_into",
    784 to "git_merge_no_ff",
    785 to "git_merge_pending_hint",
    786 to "git_merge_pending_title",
    787 to "git_modified_count",
    788 to "git_modified_label",
    789 to "git_new",
    790 to "git_new_branch",
    791 to "git_new_branch_name",
    792 to "git_no_branch",
    793 to "git_no_branches",
    794 to "git_no_checked_out_branch",
    795 to "git_no_commits",
    796 to "git_no_files_changed",
    797 to "git_no_identity",
    798 to "git_no_more_commits",
    799 to "git_no_upstream",
    800 to "git_not_a_repo",
    801 to "git_open_in_editor",
    802 to "git_pick_existing_repo",
    803 to "git_pick_repo_empty",
    804 to "git_pull",
    805 to "git_pull_continue",
    806 to "git_pull_dirty_confirm",
    807 to "git_push",
    808 to "git_push_keep_master",
    809 to "git_push_master_confirm_desc",
    810 to "git_push_master_confirm_title",
    811 to "git_push_rename_to_main",
    812 to "git_recent_commit",
    813 to "git_refresh",
    814 to "git_remote",
    815 to "git_remote_url",
    816 to "git_rename_branch",
    817 to "git_rename_to",
    818 to "git_repo_not_repo",
    819 to "git_repo_selector_title",
    820 to "git_repo_url_title",
    821 to "git_reset_confirm",
    822 to "git_reset_mixed",
    823 to "git_reset_soft",
    824 to "git_reset_to_commit",
    825 to "git_restore_file_confirm",
    826 to "git_revert_all_confirm",
    827 to "git_revert_confirm_head",
    828 to "git_revert_confirm_worktree",
    829 to "git_search_commits_hint",
    830 to "git_stage",
    831 to "git_stage_all",
    832 to "git_staged_count",
    833 to "git_staged_label",
    834 to "git_stash_apply",
    835 to "git_stash_clear",
    836 to "git_stash_clear_confirm",
    837 to "git_stash_count",
    838 to "git_stash_drop",
    839 to "git_stash_drop_confirm",
    840 to "git_stash_empty",
    841 to "git_stash_include_untracked",
    842 to "git_stash_message_hint",
    843 to "git_stash_pop",
    844 to "git_stash_title",
    845 to "git_status_clean",
    846 to "git_switch_branch",
    847 to "git_switch_confirm",
    848 to "git_switch_repo",
    849 to "git_tab_branches",
    850 to "git_tab_commits",
    851 to "git_tab_credentials",
    852 to "git_tab_identity",
    853 to "git_tab_stash",
    854 to "git_tab_status",
    855 to "git_tag_name",
    856 to "git_tags",
    857 to "git_title",
    858 to "git_toast_action_failed",
    859 to "git_toast_action_refresh_failed",
    860 to "git_toast_action_success",
    861 to "git_toast_added_to_gitignore",
    862 to "git_toast_checkout_failed",
    863 to "git_toast_checkout_success",
    864 to "git_toast_diff_failed",
    865 to "git_toast_generate_commit_failed",
    866 to "git_toast_load_branches_failed",
    867 to "git_toast_load_more_failed",
    868 to "git_toast_no_diff_for_commit",
    869 to "git_toast_no_remote_pull",
    870 to "git_toast_no_remote_push",
    871 to "git_toast_not_repo",
    872 to "git_toast_path_copied",
    873 to "git_toast_refresh_failed",
    874 to "git_toast_switch_repo_failed",
    875 to "git_tracking_branch",
    876 to "git_unstage",
    877 to "git_unstage_all_confirm",
    878 to "git_untracked_count",
    879 to "git_untracked_label",
    880 to "image_gen_no_models",
    881 to "language_follow_system",
    882 to "log_clear_filter",
    883 to "log_copied",
    884 to "log_copy",
    885 to "log_copy_line",
    886 to "log_copy_menu_title",
    887 to "log_copy_message",
    888 to "log_copy_with_stack",
    889 to "log_file_location",
    890 to "log_files",
    891 to "log_follow_latest",
    892 to "log_level",
    893 to "log_level_desc",
    894 to "log_mcp_prefix",
    895 to "log_no_match",
    896 to "log_search_hint",
    897 to "log_search_results",
    898 to "log_show_last_lines",
    899 to "log_show_lines",
    900 to "mcp_add",
    901 to "mcp_add_arg",
    902 to "mcp_add_env_var",
    903 to "mcp_add_from_json",
    904 to "mcp_add_from_json_desc",
    905 to "mcp_add_header",
    906 to "mcp_add_manual",
    907 to "mcp_add_manual_desc",
    908 to "mcp_add_sheet_title",
    909 to "mcp_arg_hint",
    910 to "mcp_arg_value",
    911 to "mcp_args",
    912 to "mcp_auth_advanced",
    913 to "mcp_auth_cancel",
    914 to "mcp_auth_client_id",
    915 to "mcp_auth_client_id_hint",
    916 to "mcp_auth_desc",
    917 to "mcp_auth_enable",
    918 to "mcp_auth_failed",
    919 to "mcp_auth_in_progress",
    920 to "mcp_auth_in_progress_hint",
    921 to "mcp_auth_login",
    922 to "mcp_auth_logout",
    923 to "mcp_auth_reauthorize",
    924 to "mcp_auth_save_first",
    925 to "mcp_auth_server",
    926 to "mcp_auth_server_hint",
    927 to "mcp_auth_status_authorized",
    928 to "mcp_auth_status_authorizing",
    929 to "mcp_auth_status_label",
    930 to "mcp_auth_status_unauthorized",
    931 to "mcp_auth_success",
    932 to "mcp_basic_settings",
    933 to "mcp_batch_delete_confirm",
    934 to "mcp_batch_delete_title",
    935 to "mcp_command",
    936 to "mcp_command_hint",
    937 to "mcp_connected",
    938 to "mcp_connecting",
    939 to "mcp_connection_failed",
    940 to "mcp_custom_headers",
    941 to "mcp_details",
    942 to "mcp_disabled",
    943 to "mcp_edit",
    944 to "mcp_empty",
    945 to "mcp_empty_hint",
    946 to "mcp_enabled",
    947 to "mcp_env_name",
    948 to "mcp_env_name_hint",
    949 to "mcp_env_value",
    950 to "mcp_env_value_hint",
    951 to "mcp_env_vars",
    952 to "mcp_header_name",
    953 to "mcp_header_name_hint",
    954 to "mcp_header_value",
    955 to "mcp_header_value_hint",
    956 to "mcp_json_import_action",
    957 to "mcp_json_import_empty",
    958 to "mcp_json_import_hint",
    959 to "mcp_json_import_title",
    960 to "mcp_local_stdio",
    961 to "mcp_name_hint",
    962 to "mcp_name_invalid",
    963 to "mcp_needs_auth",
    964 to "mcp_no_args",
    965 to "mcp_no_custom_headers",
    966 to "mcp_no_description",
    967 to "mcp_no_env_vars",
    968 to "mcp_no_tools_hint",
    969 to "mcp_not_connected",
    970 to "mcp_refresh_tools",
    971 to "mcp_remote_http",
    972 to "mcp_save_first_hint",
    973 to "mcp_scope_global",
    974 to "mcp_scope_label",
    975 to "mcp_scope_project",
    976 to "mcp_search_empty",
    977 to "mcp_search_placeholder",
    978 to "mcp_server_url",
    979 to "mcp_server_url_hint",
    980 to "mcp_tools_count",
    981 to "mcp_tools_disable_all",
    982 to "mcp_tools_enable_all",
    983 to "mcp_tools_search",
    984 to "mcp_tools_search_empty",
    985 to "mcp_tools_summary",
    986 to "mcp_transport_type",
    987 to "mcp_type_stdio",
    988 to "mcp_view_logs",
    989 to "network_error_connect",
    990 to "network_error_dns",
    991 to "network_error_ssl",
    992 to "network_error_timeout",
    993 to "notification_agent_live_title_multi",
    994 to "notification_approval_action_allow",
    995 to "notification_approval_action_approve_plan",
    996 to "notification_approval_action_reject",
    997 to "notification_approval_label_permission",
    998 to "notification_approval_label_plan",
    999 to "notification_approval_label_question",
    1000 to "notification_approval_multi_title",
    1001 to "notification_approval_needed",
    1002 to "notification_approval_title",
    1003 to "notification_channel_agent_approval",
    1004 to "notification_channel_agent_approval_desc",
    1005 to "notification_channel_agent_complete",
    1006 to "notification_channel_agent_complete_desc",
    1007 to "notification_channel_agent_live",
    1008 to "notification_channel_agent_live_desc",
    1009 to "notification_channel_terminal_service",
    1010 to "notification_channel_terminal_service_desc",
    1011 to "notification_keepalive_enabled",
    1012 to "notification_keepalive_persistent_sessions",
    1013 to "notification_keepalive_running_sessions",
    1014 to "onboarding_choose_model_in_sheet_desc",
    1015 to "onboarding_choose_model_in_sheet_title",
    1016 to "onboarding_config_provider_desc",
    1017 to "onboarding_config_provider_title",
    1018 to "onboarding_done",
    1019 to "onboarding_enter_settings_desc",
    1020 to "onboarding_enter_settings_title",
    1021 to "onboarding_next",
    1022 to "onboarding_open_model_picker_desc",
    1023 to "onboarding_open_model_picker_title",
    1024 to "onboarding_open_sidebar_desc",
    1025 to "onboarding_open_sidebar_title",
    1026 to "onboarding_provider_add_desc",
    1027 to "onboarding_provider_add_title",
    1028 to "onboarding_provider_config_info_desc",
    1029 to "onboarding_provider_config_info_title",
    1030 to "onboarding_provider_fetch_models_desc",
    1031 to "onboarding_provider_fetch_models_title",
    1032 to "onboarding_provider_select_fetched_model_desc",
    1033 to "onboarding_provider_select_fetched_model_title",
    1034 to "onboarding_send_message_desc",
    1035 to "onboarding_send_message_title",
    1036 to "onboarding_skip",
    1037 to "onboarding_step_badge",
    1038 to "perm_current_project",
    1039 to "perm_current_project_none",
    1040 to "perm_deny",
    1041 to "perm_disable_safety_interception",
    1042 to "perm_disable_safety_interception_desc",
    1043 to "perm_entire_tool",
    1044 to "perm_global",
    1045 to "perm_no_global_rules",
    1046 to "perm_no_project_rules",
    1047 to "perm_promote_to_global",
    1048 to "perm_rules_short",
    1049 to "perm_safety_confirm_action",
    1050 to "perm_safety_confirm_action_ready",
    1051 to "perm_safety_confirm_message",
    1052 to "perm_safety_confirm_title",
    1053 to "perm_safety_group",
    1054 to "plan_mode_already_active",
    1055 to "plan_mode_approved_auto",
    1056 to "plan_mode_approved_build",
    1057 to "plan_mode_enter_action",
    1058 to "plan_mode_entered",
    1059 to "plan_mode_exit_action",
    1060 to "plan_mode_exited",
    1061 to "plan_mode_external_auto",
    1062 to "plan_mode_external_build",
    1063 to "plan_mode_goal_active",
    1064 to "plan_mode_invalid_action",
    1065 to "plan_mode_not_active",
    1066 to "plan_mode_reason_unspecified",
    1067 to "plan_mode_refinement_requested",
    1068 to "plan_mode_request_details",
    1069 to "plan_mode_request_summary",
    1070 to "plan_mode_request_title",
    1071 to "plan_mode_session_missing",
    1072 to "plan_mode_session_required",
    1073 to "prompts_add_prompt",
    1074 to "prompts_batch_delete_confirm",
    1075 to "prompts_batch_delete_title",
    1076 to "prompts_disable_builtin",
    1077 to "prompts_disable_builtin_hint",
    1078 to "prompts_editor_edit",
    1079 to "prompts_editor_new",
    1080 to "prompts_empty",
    1081 to "prompts_field_basic",
    1082 to "prompts_field_content",
    1083 to "prompts_field_content_placeholder",
    1084 to "prompts_field_number",
    1085 to "prompts_field_number_placeholder",
    1086 to "prompts_field_summary",
    1087 to "prompts_field_summary_placeholder",
    1088 to "prompts_field_title",
    1089 to "prompts_field_title_placeholder",
    1090 to "prompts_help",
    1091 to "prompts_reset_confirm",
    1092 to "prompts_reset_custom",
    1093 to "prompts_sort_long_press",
    1094 to "prompts_source_builtin",
    1095 to "prompts_source_global",
    1096 to "prompts_source_local",
    1097 to "prompts_source_project",
    1098 to "prompts_title",
    1099 to "provider_add",
    1100 to "provider_add_all_models",
    1101 to "provider_add_all_models_confirm",
    1102 to "provider_add_all_models_title",
    1103 to "provider_add_model",
    1104 to "provider_add_sheet_title",
    1105 to "provider_api_key",
    1106 to "provider_api_key_required",
    1107 to "provider_base_url",
    1108 to "provider_brand_doubao",
    1109 to "provider_brand_hunyuan",
    1110 to "provider_brand_siliconflow",
    1111 to "provider_brand_tongyi_qianwen",
    1112 to "provider_brand_zhipu",
    1113 to "provider_cache_anthropic_subtitle",
    1114 to "provider_cache_anthropic_title",
    1115 to "provider_cache_openai_chat_subtitle",
    1116 to "provider_cache_openai_chat_title",
    1117 to "provider_config",
    1118 to "provider_custom",
    1119 to "provider_dashboard_clear_script",
    1120 to "provider_dashboard_loading",
    1121 to "provider_dashboard_no_scripts",
    1122 to "provider_dashboard_preview_title",
    1123 to "provider_dashboard_raw_output",
    1124 to "provider_dashboard_run_test",
    1125 to "provider_dashboard_script",
    1126 to "provider_dashboard_script_desc",
    1127 to "provider_dashboard_script_placeholder",
    1128 to "provider_dashboard_select_script",
    1129 to "provider_dashboard_select_title",
    1130 to "provider_dashboard_test_btn",
    1131 to "provider_dashboard_test_failed",
    1132 to "provider_edit",
    1133 to "provider_edit_model",
    1134 to "provider_fetch_failed",
    1135 to "provider_fetch_http_error",
    1136 to "provider_fetch_missing_fields",
    1137 to "provider_fetch_missing_fields_detail",
    1138 to "provider_fetch_models",
    1139 to "provider_fetch_success",
    1140 to "provider_filter_models_hint",
    1141 to "provider_full_url",
    1142 to "provider_full_url_desc",
    1143 to "provider_headers_add",
    1144 to "provider_headers_count",
    1145 to "provider_headers_desc",
    1146 to "provider_headers_empty",
    1147 to "provider_headers_manage",
    1148 to "provider_headers_name",
    1149 to "provider_headers_name_hint",
    1150 to "provider_headers_remove",
    1151 to "provider_headers_title",
    1152 to "provider_headers_value",
    1153 to "provider_headers_value_hint",
    1154 to "provider_hide_api_key",
    1155 to "provider_interactions_api",
    1156 to "provider_model_already_added",
    1157 to "provider_model_cap_image_output",
    1158 to "provider_model_cap_vision",
    1159 to "provider_model_capabilities",
    1160 to "provider_model_capability_reasoning",
    1161 to "provider_model_capability_tools",
    1162 to "provider_model_context_input",
    1163 to "provider_model_context_output",
    1164 to "provider_model_context_total",
    1165 to "provider_model_icon",
    1166 to "provider_model_name",
    1167 to "provider_model_price_cache_read",
    1168 to "provider_model_price_cache_write",
    1169 to "provider_model_price_input",
    1170 to "provider_model_price_output",
    1171 to "provider_model_reasoning_can_disable",
    1172 to "provider_model_reasoning_hint",
    1173 to "provider_model_section_price",
    1174 to "provider_model_section_reasoning",
    1175 to "provider_model_window_invalid",
    1176 to "provider_models_aligned_removed",
    1177 to "provider_models_batch_delete",
    1178 to "provider_models_batch_delete_confirm",
    1179 to "provider_models_batch_delete_title",
    1180 to "provider_models_batch_test",
    1181 to "provider_models_count",
    1182 to "provider_models_count_tag",
    1183 to "provider_models_deselect_all",
    1184 to "provider_models_select",
    1185 to "provider_models_select_all",
    1186 to "provider_models_selected_count",
    1187 to "provider_multi_key_add",
    1188 to "provider_multi_key_cooldown",
    1189 to "provider_multi_key_cooldown_desc",
    1190 to "provider_multi_key_cooldown_off",
    1191 to "provider_multi_key_cooldown_value",
    1192 to "provider_multi_key_empty",
    1193 to "provider_multi_key_failover",
    1194 to "provider_multi_key_item",
    1195 to "provider_multi_key_list",
    1196 to "provider_multi_key_manage",
    1197 to "provider_multi_key_status_codes",
    1198 to "provider_multi_key_status_codes_hint",
    1199 to "provider_multi_key_strategy",
    1200 to "provider_multi_key_strategy_round_robin",
    1201 to "provider_multi_key_strategy_round_robin_desc",
    1202 to "provider_multi_key_strategy_sequential",
    1203 to "provider_multi_key_strategy_sequential_desc",
    1204 to "provider_multi_key_subtitle",
    1205 to "provider_multi_key_summary",
    1206 to "provider_multi_key_title",
    1207 to "provider_multi_key_weight",
    1208 to "provider_multi_key_weight_hint",
    1209 to "provider_new",
    1210 to "provider_no_matching_models",
    1211 to "provider_no_models",
    1212 to "provider_no_models_hint",
    1213 to "provider_please_wait",
    1214 to "provider_preset_empty",
    1215 to "provider_preset_loading",
    1216 to "provider_proxy_enable",
    1217 to "provider_proxy_enable_desc",
    1218 to "provider_proxy_off",
    1219 to "provider_proxy_section",
    1220 to "provider_register_now",
    1221 to "provider_response_api",
    1222 to "provider_script_param_add",
    1223 to "provider_script_param_key",
    1224 to "provider_script_param_key_hint",
    1225 to "provider_script_param_manage",
    1226 to "provider_script_param_remove",
    1227 to "provider_script_param_value",
    1228 to "provider_script_param_value_hint",
    1229 to "provider_script_params_count",
    1230 to "provider_script_params_desc",
    1231 to "provider_script_params_empty",
    1232 to "provider_script_params_title",
    1233 to "provider_section_basic",
    1234 to "provider_section_dashboard",
    1235 to "provider_section_options",
    1236 to "provider_section_type",
    1237 to "provider_show_api_key",
    1238 to "provider_sort_long_press",
    1239 to "provider_test",
    1240 to "provider_test_detail_title",
    1241 to "provider_test_error_detail",
    1242 to "provider_test_req_body",
    1243 to "provider_test_req_headers",
    1244 to "provider_test_req_url",
    1245 to "provider_test_resp_body",
    1246 to "provider_test_resp_headers",
    1247 to "provider_test_success",
    1248 to "providers_batch_delete_confirm",
    1249 to "providers_batch_delete_title",
    1250 to "providers_empty",
    1251 to "proxy_auth",
    1252 to "proxy_enable",
    1253 to "proxy_enable_subtitle",
    1254 to "proxy_host",
    1255 to "proxy_host_placeholder",
    1256 to "proxy_no_proxy",
    1257 to "proxy_no_proxy_hint",
    1258 to "proxy_password",
    1259 to "proxy_port",
    1260 to "proxy_port_placeholder",
    1261 to "proxy_server",
    1262 to "proxy_test",
    1263 to "proxy_test_connect_failed",
    1264 to "proxy_test_http_error",
    1265 to "proxy_test_invalid_config",
    1266 to "proxy_test_ok",
    1267 to "proxy_test_title",
    1268 to "proxy_test_url_label",
    1269 to "proxy_test_url_placeholder",
    1270 to "proxy_testing",
    1271 to "proxy_title",
    1272 to "proxy_type",
    1273 to "proxy_type_http",
    1274 to "proxy_type_socks5",
    1275 to "proxy_username",
    1276 to "remote_add_channel_first",
    1277 to "remote_add_connection",
    1278 to "remote_add_workspace",
    1279 to "remote_auth_key",
    1280 to "remote_auth_password",
    1281 to "remote_auto_connect_and_sync",
    1282 to "remote_auto_connect_on_start",
    1283 to "remote_browse_dir",
    1284 to "remote_clear_index_done",
    1285 to "remote_confirm_select",
    1286 to "remote_connect_failed",
    1287 to "remote_connect_success",
    1288 to "remote_connection_name_hint",
    1289 to "remote_connection_sort_long_press",
    1290 to "remote_current_path",
    1291 to "remote_delete_connection_confirm",
    1292 to "remote_delete_mount_confirm",
    1293 to "remote_download_all_failed",
    1294 to "remote_download_all_success",
    1295 to "remote_edit_connection",
    1296 to "remote_edit_workspace",
    1297 to "remote_go_add",
    1298 to "remote_hint_title",
    1299 to "remote_host_address",
    1300 to "remote_key_passphrase",
    1301 to "remote_link_channel",
    1302 to "remote_load_failed",
    1303 to "remote_login_key",
    1304 to "remote_map_to_local",
    1305 to "remote_mount_already_exists",
    1306 to "remote_no_connections",
    1307 to "remote_no_connections_desc",
    1308 to "remote_no_keys_hint",
    1309 to "remote_no_local_workspace",
    1310 to "remote_no_workspaces",
    1311 to "remote_no_workspaces_desc",
    1312 to "remote_parent_dir",
    1313 to "remote_password",
    1314 to "remote_port",
    1315 to "remote_select_channel",
    1316 to "remote_select_key",
    1317 to "remote_select_local_workspace",
    1318 to "remote_select_remote_dir",
    1319 to "remote_tab_connections",
    1320 to "remote_tab_ftp",
    1321 to "remote_tab_mounts",
    1322 to "remote_tab_sync",
    1323 to "remote_target_dir",
    1324 to "remote_test_connection",
    1325 to "remote_toggle_password",
    1326 to "remote_unknown_error",
    1327 to "remote_upload_failed",
    1328 to "remote_upload_full_failed",
    1329 to "remote_upload_full_success",
    1330 to "remote_upload_success",
    1331 to "remote_workspace_title",
    1332 to "retry_error_connection_refused",
    1333 to "retry_error_connection_reset",
    1334 to "retry_error_dns_failed",
    1335 to "retry_error_network",
    1336 to "retry_error_rate_limit",
    1337 to "retry_error_server",
    1338 to "retry_error_server_overloaded",
    1339 to "retry_error_ssl",
    1340 to "retry_error_timeout",
    1341 to "retry_error_unknown",
    1342 to "retry_error_with_code",
    1343 to "session_group_last_30_days",
    1344 to "session_group_last_7_days",
    1345 to "session_group_month_format",
    1346 to "session_group_pinned",
    1347 to "session_group_today",
    1348 to "session_group_yesterday",
    1349 to "settings_about",
    1350 to "settings_add_mcp_server",
    1351 to "settings_add_provider",
    1352 to "settings_agent_sound_subtitle",
    1353 to "settings_agent_sound_title",
    1354 to "settings_app_permissions",
    1355 to "settings_approval_notification_subtitle",
    1356 to "settings_approval_notification_title",
    1357 to "settings_auto_remove_stale_models",
    1358 to "settings_auto_remove_stale_models_desc",
    1359 to "settings_autostart_subtitle",
    1360 to "settings_autostart_title",
    1361 to "settings_background_image",
    1362 to "settings_background_image_alpha",
    1363 to "settings_background_image_none",
    1364 to "settings_background_image_pick",
    1365 to "settings_background_image_remove",
    1927 to "team_member_add",
    1925 to "team_editor_add_agent",
    1926 to "team_editor_add_model",
    1872 to "settings_background_kind_gif",
    1873 to "settings_background_kind_video",
    1881 to "settings_teams",
    1882 to "chat_open_teams",
    1883 to "teams_new",
    1884 to "teams_empty",
    1885 to "teams_empty_hint",
    1886 to "teams_list_header",
    1887 to "teams_member_count",
    1888 to "team_editor_new",
    1889 to "team_editor_edit",
    1890 to "team_editor_basic",
    1891 to "team_editor_owner",
    1892 to "team_editor_owner_name",
    1893 to "team_editor_pick_avatar",
    1894 to "team_editor_members",
    1895 to "team_editor_members_hint",
    1896 to "team_editor_rename_member",
    1897 to "team_editor_remove_member",
    1898 to "team_editor_autocompose",
    1899 to "team_editor_autocompose_action",
    1900 to "team_editor_autocompose_placeholder",
    1901 to "team_editor_autocomposing",
    1902 to "team_editor_no_agents",
    1903 to "team_editor_no_models",
    1904 to "team_editor_no_members",
    1905 to "team_member_lead",
    1906 to "team_member_prompt_hint",
    1907 to "team_name_hint",
    1908 to "team_description_hint",
    1909 to "team_strategy_hint",
    1910 to "team_error_invalid_name",
    1911 to "team_error_name_conflict",
    1912 to "team_error_member_limit",
    1913 to "team_error_no_members",
    1914 to "team_delete",
    1915 to "team_delete_confirm",
    1916 to "team_chat_title",
    1917 to "team_chat_input_hint",
    1918 to "team_chat_send",
    1919 to "team_chat_stop",
    1920 to "team_chat_thinking",
    1921 to "team_chat_empty",
    1922 to "team_chat_empty_hint",
    1923 to "team_chat_clear",
    1924 to "team_chat_clear_confirm",
    1366 to "settings_backup",
    1367 to "settings_category_ai",
    1368 to "settings_category_background",
    1369 to "settings_category_data",
    1370 to "settings_category_environment",
    1371 to "settings_category_general",
    1372 to "settings_category_help",
    1373 to "settings_category_permissions",
    1374 to "settings_category_system_permissions",
    1375 to "settings_compaction_follow_chat",
    1376 to "settings_compaction_model",
    1377 to "settings_compaction_threshold",
    1378 to "settings_compaction_threshold_desc",
    1379 to "settings_compaction_threshold_input_hint",
    1380 to "settings_container",
    1381 to "settings_default_models",
    1382 to "settings_delete_external_workspace_sessions",
    1383 to "settings_delete_external_workspace_sessions_desc",
    1384 to "settings_enter_to_send",
    1385 to "settings_enter_to_send_desc",
    1386 to "settings_first_byte_timeout",
    1387 to "settings_first_byte_timeout_desc",
    1388 to "settings_general",
    1389 to "settings_general_goal",
    1390 to "settings_general_input",
    1391 to "settings_general_models",
    1392 to "settings_general_network",
    1393 to "settings_general_session",
    1394 to "settings_general_tools",
    1395 to "settings_goal_max_tokens",
    1396 to "settings_goal_max_tokens_desc",
    1397 to "settings_goal_max_tokens_input_hint",
    1398 to "settings_goal_max_turns",
    1399 to "settings_goal_max_turns_desc",
    1400 to "settings_goal_max_turns_input_hint",
    1401 to "settings_image_gen_model",
    1402 to "settings_image_gen_unconfigured",
    1403 to "settings_keepalive_subtitle",
    1404 to "settings_keepalive_title",
    1405 to "settings_language",
    1406 to "settings_live_notification_subtitle",
    1407 to "settings_live_notification_title",
    1408 to "settings_log",
    1409 to "settings_log_no_files",
    1410 to "settings_log_read_failed",
    1411 to "settings_max_network_retries",
    1412 to "settings_max_network_retries_desc",
    1413 to "settings_mb_value",
    1414 to "settings_mcp",
    1415 to "settings_models_fetch_failed",
    1416 to "settings_network_guide_brand_huawei",
    1417 to "settings_network_guide_brand_oppo",
    1418 to "settings_network_guide_brand_samsung",
    1419 to "settings_network_guide_brand_vivo",
    1420 to "settings_network_guide_brand_xiaomi",
    1421 to "settings_network_guide_intro",
    1422 to "settings_network_guide_more",
    1423 to "settings_network_guide_step_battery",
    1424 to "settings_network_guide_step_keepalive",
    1425 to "settings_network_guide_step_lock",
    1426 to "settings_network_guide_subtitle",
    1427 to "settings_network_guide_title",
    1428 to "settings_percent_value",
    1429 to "settings_permission_apk_install",
    1430 to "settings_permission_apk_install_desc",
    1431 to "settings_permission_battery",
    1432 to "settings_permission_battery_desc",
    1433 to "settings_permission_denied",
    1434 to "settings_permission_granted",
    1435 to "settings_permission_notification_denied_desc",
    1436 to "settings_permission_notification_title",
    1437 to "settings_permission_storage",
    1438 to "settings_permission_storage_denied_desc",
    1439 to "settings_permission_storage_desc",
    1440 to "settings_permissions",
    1441 to "settings_provider_editor",
    1442 to "settings_providers",
    1443 to "settings_reconnect",
    1444 to "settings_refresh_logs",
    1445 to "settings_remote_servers",
    1446 to "settings_rerun_onboarding",
    1447 to "settings_retries_count",
    1448 to "settings_retries_input_hint",
    1449 to "settings_screen_on_subtitle",
    1450 to "settings_screen_on_title",
    1451 to "settings_select_section_hint",
    1452 to "settings_sendfile_max_size",
    1453 to "settings_sendfile_max_size_desc",
    1454 to "settings_sendfile_max_size_input_hint",
    1455 to "settings_shizuku",
    1456 to "settings_shizuku_hint_denied",
    1457 to "settings_shizuku_hint_not_installed",
    1458 to "settings_shizuku_hint_not_running",
    1459 to "settings_shizuku_hint_ready",
    1460 to "settings_shizuku_status_denied",
    1461 to "settings_shizuku_status_not_installed",
    1462 to "settings_shizuku_status_not_running",
    1463 to "settings_shizuku_status_ready",
    1464 to "settings_skills",
    1465 to "settings_startup_session",
    1466 to "settings_startup_session_new",
    1467 to "settings_startup_session_new_desc",
    1468 to "settings_startup_session_recent",
    1469 to "settings_startup_session_recent_desc",
    1470 to "settings_storage",
    1471 to "settings_stream_idle_timeout",
    1472 to "settings_stream_idle_timeout_desc",
    1473 to "settings_subagent_tier_light",
    1474 to "settings_subagent_tier_standard",
    1475 to "settings_subagent_tier_strong",
    1476 to "settings_subagent_tier_unconfigured",
    1477 to "settings_subagent_tiers_header",
    1478 to "settings_subagents",
    1479 to "settings_theme_title",
    1480 to "settings_timeout_input_hint",
    1481 to "settings_timeout_seconds",
    1482 to "settings_timeout_unlimited",
    1483 to "settings_title",
    1484 to "settings_title_follow_chat",
    1485 to "settings_title_model",
    1486 to "settings_token_stats_cache_hit_rate",
    1487 to "settings_token_stats_cache_read_write",
    1488 to "settings_token_stats_calls_suffix",
    1489 to "settings_token_stats_calls_suffix2",
    1490 to "settings_token_stats_col_cache_hit_rate",
    1491 to "settings_token_stats_col_cached_read",
    1492 to "settings_token_stats_col_cached_write",
    1493 to "settings_token_stats_col_cost",
    1494 to "settings_token_stats_col_duration",
    1495 to "settings_token_stats_col_input",
    1496 to "settings_token_stats_col_model",
    1497 to "settings_token_stats_col_output",
    1498 to "settings_token_stats_col_provider",
    1499 to "settings_token_stats_col_reasoning_effort",
    1500 to "settings_token_stats_col_reasoning_wire",
    1501 to "settings_token_stats_col_status",
    1502 to "settings_token_stats_col_time",
    1503 to "settings_token_stats_col_ttfb",
    1504 to "settings_token_stats_cost",
    1505 to "settings_token_stats_filter_all_models",
    1506 to "settings_token_stats_filter_all_providers",
    1507 to "settings_token_stats_filter_clear",
    1508 to "settings_token_stats_filter_model",
    1509 to "settings_token_stats_filter_provider",
    1510 to "settings_token_stats_legend_cached",
    1511 to "settings_token_stats_legend_input",
    1512 to "settings_token_stats_legend_output",
    1513 to "settings_token_stats_model",
    1514 to "settings_token_stats_no_data",
    1515 to "settings_token_stats_page",
    1516 to "settings_token_stats_period_30d",
    1517 to "settings_token_stats_period_7d",
    1518 to "settings_token_stats_period_all",
    1519 to "settings_token_stats_period_today",
    1520 to "settings_token_stats_provider",
    1521 to "settings_token_stats_provider_keys",
    1522 to "settings_token_stats_recent_calls",
    1523 to "settings_token_stats_requests",
    1524 to "settings_token_stats_requests_unit",
    1525 to "settings_token_stats_reset",
    1526 to "settings_token_stats_reset_confirm",
    1527 to "settings_token_stats_reset_title",
    1528 to "settings_token_stats_retries",
    1529 to "settings_token_stats_success_rate",
    1530 to "settings_token_stats_title",
    1531 to "settings_token_stats_total",
    1532 to "settings_token_stats_trend",
    1533 to "settings_token_stats_unknown",
    1534 to "settings_tokens_value",
    1535 to "settings_turns_value",
    1536 to "settings_user_guide",
    1537 to "settings_vision_follow_chat",
    1538 to "settings_vision_model",
    1539 to "skills_add",
    1540 to "skills_add_from_file",
    1541 to "skills_add_from_file_desc",
    1542 to "skills_add_from_zip",
    1543 to "skills_add_from_zip_desc",
    1544 to "skills_add_manual",
    1545 to "skills_add_manual_desc",
    1546 to "skills_add_sheet_title",
    1547 to "skills_batch_delete_confirm",
    1548 to "skills_batch_delete_title",
    1549 to "skills_delete_confirm_message",
    1550 to "skills_delete_confirm_title",
    1551 to "skills_description",
    1552 to "skills_description_hint",
    1553 to "skills_edit",
    1554 to "skills_editor_basic",
    1555 to "skills_editor_edit",
    1556 to "skills_editor_error_empty_instructions",
    1557 to "skills_editor_error_invalid_name",
    1558 to "skills_editor_error_io",
    1559 to "skills_editor_error_name_conflict",
    1560 to "skills_editor_new",
    1561 to "skills_editor_scope",
    1562 to "skills_empty",
    1563 to "skills_empty_hint",
    1564 to "skills_enable",
    1565 to "skills_import_error_empty_content",
    1566 to "skills_import_error_invalid_archive",
    1567 to "skills_import_error_invalid_name",
    1568 to "skills_import_error_io",
    1569 to "skills_import_error_name_conflict",
    1570 to "skills_import_error_no_skill",
    1571 to "skills_import_error_unsupported_file",
    1572 to "skills_import_failure_line",
    1573 to "skills_import_partial",
    1574 to "skills_import_result_title",
    1575 to "skills_import_running",
    1576 to "skills_import_success",
    1577 to "skills_instructions",
    1578 to "skills_instructions_hint",
    1579 to "skills_name_hint",
    1580 to "skills_no_global_skills",
    1581 to "skills_no_project_skills",
    1582 to "skills_scope_global",
    1583 to "skills_scope_project",
    1584 to "skills_summary",
    1585 to "slash_command_compress_desc",
    1586 to "slash_command_goal_desc",
    1587 to "slash_command_init_desc",
    1588 to "slash_command_usage_desc",
    1589 to "ssh_host_key_changed_title",
    1590 to "ssh_host_key_confirm_title",
    1591 to "ssh_host_key_detail",
    1592 to "ssh_host_key_empty",
    1593 to "ssh_host_key_fingerprint_value",
    1594 to "ssh_host_key_reject",
    1595 to "ssh_host_key_remove",
    1596 to "ssh_host_key_settings_desc",
    1597 to "ssh_host_key_settings_title",
    1598 to "ssh_host_key_tab_fingerprints",
    1599 to "ssh_host_key_tab_keys",
    1600 to "ssh_host_key_trust",
    1601 to "ssh_login_key_add",
    1602 to "ssh_login_key_add_from_file",
    1603 to "ssh_login_key_add_from_file_desc",
    1604 to "ssh_login_key_add_from_paste",
    1605 to "ssh_login_key_add_from_paste_desc",
    1606 to "ssh_login_key_add_method_title",
    1607 to "ssh_login_key_content_label",
    1608 to "ssh_login_key_edit_title",
    1609 to "ssh_login_key_empty",
    1610 to "ssh_login_key_encrypted",
    1611 to "ssh_login_key_fingerprint_label",
    1612 to "ssh_login_key_name_label",
    1613 to "ssh_login_key_passphrase_placeholder",
    1614 to "ssh_login_key_paste_content_placeholder",
    1615 to "ssh_login_key_paste_invalid",
    1616 to "ssh_login_key_paste_name_placeholder",
    1617 to "ssh_login_key_paste_title",
    1618 to "ssh_login_key_toggle_content",
    1619 to "status_connected",
    1620 to "status_connection_failed",
    1621 to "status_disconnected",
    1622 to "status_reconnecting",
    1623 to "storage_breakdown_note",
    1624 to "storage_category_ai_config",
    1625 to "storage_category_apk",
    1626 to "storage_category_caches",
    1627 to "storage_category_chat",
    1628 to "storage_category_checkpoints",
    1629 to "storage_category_container",
    1630 to "storage_category_container_images",
    1631 to "storage_category_logs",
    1632 to "storage_category_other",
    1633 to "storage_category_workspaces",
    1634 to "storage_chat_messages",
    1635 to "storage_clean",
    1636 to "storage_clean_caches",
    1637 to "storage_clean_caches_desc",
    1638 to "storage_clean_confirm_selected",
    1639 to "storage_clean_confirm_title",
    1640 to "storage_clean_logs",
    1641 to "storage_clean_logs_desc",
    1642 to "storage_clean_scope",
    1643 to "storage_clean_selected",
    1644 to "storage_clean_tool_output",
    1645 to "storage_clean_tool_output_desc",
    1646 to "storage_clean_vision",
    1647 to "storage_clean_vision_desc",
    1648 to "storage_device_free",
    1649 to "storage_freed",
    1650 to "storage_logs_ai",
    1651 to "storage_logs_app",
    1652 to "storage_refresh",
    1653 to "storage_scanning",
    1654 to "storage_section_breakdown",
    1655 to "storage_total_title",
    1656 to "storage_untitled_session",
    1657 to "storage_workspace_attachments",
    1658 to "subagent_add",
    1659 to "subagent_all_tools",
    1660 to "subagent_card_changed_files",
    1661 to "subagent_card_collapse",
    1662 to "subagent_card_default_agent",
    1663 to "subagent_card_expand",
    1664 to "subagent_card_open",
    1665 to "subagent_card_status_completed",
    1666 to "subagent_card_status_dispatched",
    1667 to "subagent_card_status_failed",
    1668 to "subagent_card_status_running",
    1669 to "subagent_card_status_stopped",
    1670 to "subagent_card_stop",
    1671 to "subagent_description_hint",
    1672 to "subagent_dispatch_count",
    1673 to "subagent_dispatch_title",
    1674 to "subagent_edit",
    1675 to "subagent_editor_basic",
    1676 to "subagent_editor_clear",
    1677 to "subagent_editor_description",
    1678 to "subagent_editor_edit",
    1679 to "subagent_editor_effort",
    1680 to "subagent_editor_error_empty_prompt",
    1681 to "subagent_editor_error_invalid_name",
    1682 to "subagent_editor_error_io",
    1683 to "subagent_editor_error_name_conflict",
    1684 to "subagent_editor_mode",
    1685 to "subagent_editor_model_group",
    1686 to "subagent_editor_new",
    1687 to "subagent_editor_none",
    1688 to "subagent_editor_scope",
    1689 to "subagent_editor_selected_count",
    1690 to "subagent_editor_tools_allow",
    1691 to "subagent_editor_tools_allow_hint",
    1692 to "subagent_editor_tools_deny",
    1693 to "subagent_editor_tools_deny_hint",
    1694 to "subagent_editor_tools_search",
    1695 to "subagent_enable",
    1696 to "subagent_enable_hint",
    1697 to "subagent_inherit_parent",
    1698 to "subagent_inject",
    1699 to "subagent_inject_base",
    1700 to "subagent_inject_base_hint",
    1701 to "subagent_inject_main_rules",
    1702 to "subagent_inject_main_rules_hint",
    1703 to "subagent_inject_memory",
    1704 to "subagent_inject_memory_hint",
    1705 to "subagent_inject_none",
    1706 to "subagent_inject_project_rules",
    1707 to "subagent_inject_project_rules_hint",
    1708 to "subagent_inject_skills",
    1709 to "subagent_inject_skills_hint",
    1710 to "subagent_model",
    1711 to "subagent_name_hint",
    1712 to "subagent_no_models",
    1713 to "subagent_prompt",
    1714 to "subagent_prompt_hint",
    1715 to "subagent_scope_global",
    1716 to "subagent_scope_project",
    1717 to "subagent_source_file",
    1718 to "subagent_tab_sessions",
    1719 to "subagent_tools",
    1720 to "subagent_tools_allow",
    1721 to "subagent_tools_deny",
    1722 to "subagents_batch_delete_confirm",
    1723 to "subagents_batch_delete_title",
    1724 to "subagents_delete_confirm_message",
    1725 to "subagents_delete_confirm_title",
    1726 to "subagents_empty",
    1727 to "subagents_empty_hint",
    1728 to "subagents_no_global",
    1729 to "subagents_no_project",
    1730 to "sync_batch_size_desc",
    1731 to "sync_clear_index",
    1732 to "sync_connect_and_sync",
    1733 to "sync_disconnect",
    1734 to "sync_download_all",
    1735 to "sync_follow_gitignore",
    1736 to "sync_gitignore_desc",
    1737 to "sync_max_batch_count",
    1738 to "sync_max_batch_size",
    1739 to "sync_path_mapping",
    1740 to "sync_save_settings",
    1741 to "sync_saved",
    1742 to "sync_settings_title",
    1743 to "sync_unknown_connection",
    1744 to "sync_upload_changes",
    1745 to "sync_upload_full",
    1746 to "terminal_cleaning_old_container",
    1747 to "terminal_close_tab",
    1748 to "terminal_cursor_bar",
    1749 to "terminal_cursor_block",
    1750 to "terminal_cursor_style_title",
    1751 to "terminal_cursor_underline",
    1752 to "terminal_extracting_env",
    1753 to "terminal_font_builtin",
    1754 to "terminal_font_default",
    1755 to "terminal_font_delete",
    1756 to "terminal_font_import",
    1757 to "terminal_font_import_failed",
    1758 to "terminal_font_size_title",
    1759 to "terminal_font_title",
    1760 to "terminal_installing_packages",
    1761 to "terminal_no_open_tabs",
    1762 to "terminal_prepare_error_unknown",
    1763 to "terminal_preparing_env_failed",
    1764 to "terminal_preparing_env_first_run",
    1765 to "terminal_preview_text",
    1766 to "terminal_retry",
    1767 to "terminal_settings_title",
    1768 to "terminal_start_failed",
    1769 to "terminal_theme_dracula",
    1770 to "terminal_theme_follow_app",
    1771 to "terminal_theme_github_dark",
    1772 to "terminal_theme_github_light",
    1773 to "terminal_theme_monokai",
    1774 to "terminal_theme_one_dark",
    1775 to "terminal_theme_termius_dark",
    1776 to "terminal_theme_title",
    1777 to "terminal_title",
    1778 to "theme_auto",
    1779 to "theme_dark",
    1780 to "theme_dynamic_color",
    1781 to "theme_dynamic_color_desc",
    1782 to "theme_dynamic_color_unsupported",
    1783 to "theme_light",
    1784 to "theme_mode_section",
    1785 to "theme_preset_coffee",
    1786 to "theme_preset_default",
    1787 to "theme_preset_forest",
    1788 to "theme_preset_graphite",
    1789 to "theme_preset_section",
    1790 to "title_no_models",
    1791 to "todo_completed",
    1792 to "todo_dashboard_all_done",
    1793 to "todo_dashboard_progress",
    1794 to "todo_dashboard_title",
    1795 to "todo_in_progress",
    1796 to "token_stats_hour_suffix",
    1797 to "tool_browser_condition_met",
    1798 to "tool_browser_screenshot_failed",
    1799 to "tool_browser_wait_timeout",
    1800 to "tool_changed_files_summary",
    1801 to "tool_command_rule_denied",
    1802 to "tool_command_rule_denied_detail",
    1803 to "tool_command_unanalyzable_reason",
    1804 to "tool_expand_remaining",
    1805 to "tool_instruction",
    1806 to "tool_mcp_invalid_name",
    1807 to "tool_mcp_stdio_added",
    1808 to "tool_memory_saved",
    1809 to "tool_output_omitted",
    1810 to "tool_output_saved_hint",
    1811 to "tool_output_unsaved_hint",
    1812 to "tool_permission_rule_denied",
    1813 to "tool_plan_mode_denied",
    1814 to "tool_result",
    1815 to "tool_shizuku_confirmation_reason",
    1816 to "tool_subagent_message_delivered",
    1817 to "tool_subagent_plan_readonly_warning",
    1818 to "tool_subagent_provider_unresolved",
    1819 to "tool_subagent_tier_fallback",
    1820 to "tool_subagent_tier_pinned",
    1821 to "tool_subagent_tier_unavailable",
    1822 to "tool_vision_default_multiple",
    1823 to "tool_vision_default_single",
    1824 to "tool_vision_empty_response",
    1825 to "tool_vision_focus",
    1826 to "tool_vision_images_attached",
    1827 to "update_channel_latest",
    1828 to "update_channel_latest_desc",
    1829 to "update_channel_stable",
    1830 to "update_channel_stable_desc",
    1831 to "vision_no_models",
    1832 to "web_search_results_count",
    1833 to "web_search_title",
    1834 to "web_search_warning",
    1835 to "workbench_split_handle",
    1836 to "workspace_add_external",
    1837 to "workspace_confirm",
    1838 to "workspace_delete",
    1839 to "workspace_delete_confirm",
    1840 to "workspace_empty_hint",
    1841 to "workspace_external_added",
    1842 to "workspace_external_exists",
    1843 to "workspace_external_invalid",
    1844 to "workspace_external_remove_confirm",
    1845 to "workspace_external_remove_confirm_sessions",
    1846 to "workspace_external_unavailable_hint",
    1847 to "workspace_external_unresolvable",
    1848 to "workspace_external_unsupported_path",
    1849 to "workspace_external_unwritable",
    1850 to "workspace_external_warning_continue",
    1851 to "workspace_external_warning_dont_show_again",
    1852 to "workspace_external_warning_message",
    1853 to "workspace_external_warning_title",
    1854 to "workspace_name_exists",
    1855 to "workspace_name_hint",
    1856 to "workspace_new_internal",
    1857 to "workspace_new_workspace",
    1858 to "workspace_open",
    1859 to "workspace_remote_default_create_failed",
    1860 to "workspace_remote_root_create_failed",
    1861 to "workspace_select",
    1862 to "workspace_switch",
    1863 to "workspace_switch_confirm",
    1864 to "workspace_unavailable_current",
    1865 to "workspace_unavailable_fallback",
    1866 to "file_browser_set_as_workspace",
    1867 to "chat_collapse_sidebar",
    1868 to "prompts_help_body",
    1869 to "window_minimize",
    1870 to "window_maximize",
    1871 to "window_close",
    1968 to "chat_upload_video",
    1969 to "chat_upload_package",
    1974 to "about_upstream",
    1975 to "about_releases",
    1976 to "about_feedback",
    1977 to "skill_market_title",
    1978 to "market_search_hint",
    1979 to "market_install",
    1980 to "market_empty",
    1981 to "market_expand_detail",
    1982 to "market_load_more",
    1983 to "market_loading_more",
    1984 to "market_refresh",
    1985 to "market_sort_by_stars",
    1986 to "market_sort_by_name",
    1987 to "market_total_skills",
    1988 to "market_matched",
    1989 to "skill_detail_name",
    1990 to "skill_detail_display_name",
    1991 to "skill_detail_author",
    1992 to "skill_detail_stars",
    1993 to "skill_detail_version",
    1994 to "skill_detail_category",
    1995 to "skill_detail_package",
    1996 to "skill_detail_tags",
    1997 to "skill_detail_download",
    1998 to "skill_install_note",
    1999 to "market_installing",
    2000 to "market_install_success",
    2001 to "market_install_no_url",
    2002 to "market_install_failed_download",
    2003 to "market_install_failed_no_skill",
    2004 to "market_install_failed_install",
    2005 to "editor_save",
    2006 to "market_installed",
    2007 to "plugin_market_title",
    2008 to "plugin_market_section",
    2009 to "plugin_installed_section",
    2010 to "plugin_install",
    2011 to "plugin_install_url",
    2012 to "plugin_installed",
    2013 to "plugin_search_hint",
    2014 to "plugin_empty",
    2015 to "plugin_by",
    2016 to "plugin_enable",
    2017 to "plugin_disable",
    2018 to "plugin_delete_title",
    2019 to "plugin_delete_message",
    2020 to "plugin_runtime_failed",
    2021 to "plugin_tool_not_authorized",
    2022 to "plugin_tool_not_found",
    2023 to "plugin_tool_not_found_hint",
    2024 to "plugin_tool_permission_details",
    2025 to "plugin_tool_permission_summary",
    2026 to "plugin_tool_permission_title",
    2027 to "plugin_tool_unavailable",
    2028 to "plugin_web_page_failed",
    2029 to "plugin_web_page_retry",
    2030 to "plugin_detail_title",
    2031 to "plugin_page_unsupported",
    2032 to "plugin_store_title",
    2033 to "plugin_store_refresh",
    2034 to "plugin_store_install_success",
    2035 to "plugin_update_available",
    2036 to "plugin_up_to_date",
    2037 to "plugin_update",
    2038 to "plugin_install_resolving",
    2039 to "plugin_install_downloading",
    2040 to "plugin_install_installing",
    2041 to "plugin_install_progress",
)

/** 非组合上下文（ViewModel / domain）里的字符串解析：不订阅 locale 变化，只取当前值。 */
fun resolveString(id: Int): String = Strings.catalog().getValue(ID_TO_KEY.getValue(id))

/** Resolve a generated string resource id to the localized text. For MVP, just returns current value. */
fun stringResource(id: Int): String = Strings.catalog().getValue(ID_TO_KEY.getValue(id))

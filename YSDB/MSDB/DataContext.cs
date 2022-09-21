using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Migrations;
using MSDB.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace MSDB
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) : base(options)
        {
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            //唯一索引
            // modelBuilder.Entity<app_role_data>().HasIndex(a => new { a.roleId, a.functionId }).IsUnique(true);
            modelBuilder.Entity<project_member>().HasIndex(a => new { a.projectId, a.mType, a.memberId }).IsUnique(true);
            modelBuilder.Entity<project_progress_detail>().HasIndex(a => new { a.companyId, a.stage, a.year }).IsUnique(true);
            modelBuilder.Entity<project_summary>().HasIndex(a => new { a.customerId, a.projectId, a.year }).IsUnique(true);
            modelBuilder.Entity<district_summary>().HasIndex(a => new { a.deptId, a.year }).IsUnique(true);
            modelBuilder.Entity<patentData>().HasIndex(a => new { a.patentNo }).IsUnique(true);
            modelBuilder.Entity<tech_summary>().HasIndex(a => new { a.projectId }).IsUnique(true);
            modelBuilder.Entity<project_summary_data>().HasIndex(a => new { a.customerId, a.year }).IsUnique(true);
            modelBuilder.Entity<flowInstance_project>().HasIndex(a => new { a.companyId, a.year, a.projectId, a.moduleId }).IsUnique(true);
            modelBuilder.Entity<flowInstance_docFile>().HasIndex(a => new { a.docFileId }).IsUnique(true);
            modelBuilder.Entity<flowInstance_patent>().HasIndex(a => new { a.patentPlanId }).IsUnique(true);
            modelBuilder.Entity<flowInstance_report>().HasIndex(a => new { a.rsProjectId, a.moduleId }).IsUnique(true);
            modelBuilder.Entity<solution_share>().HasIndex(a => new { a.solutionId, a.type, a.dataId }).IsUnique(true);
            modelBuilder.Entity<innovation_project>().HasIndex(a => new { a.customerId, a.year }).IsUnique(true);
            modelBuilder.Entity<innovation_member>().HasIndex(a => new { a.innovationId, a.mType, a.memberId }).IsUnique(true);
            modelBuilder.Entity<rsProject_summary>().HasIndex(a => new { a.companyId, a.year, a.rsProjectId }).IsUnique(true);
            modelBuilder.Entity<daily_report>().HasIndex(a => new { a.userId, a.workDate, a.companyId }).IsUnique(true);
            modelBuilder.Entity<daily_report_detail>().HasIndex(a => new { a.userId, a.workDate, a.moduleId, a.companyId }).IsUnique(true);
            modelBuilder.Entity<monthly_report>().HasIndex(a => new { a.userId, a.workMonth }).IsUnique(true);
            modelBuilder.Entity<monthly_report_detail>().HasIndex(a => new { a.userId, a.workMonth, a.moduleId }).IsUnique(true);
            modelBuilder.Entity<patent_buying_demand>().HasIndex(a => new { a.customerId, a.year, a.type }).IsUnique(true);
            modelBuilder.Entity<patent_audit>().HasIndex(a => new { a.patentPlanId }).IsUnique(true);
            modelBuilder.Entity<flowInstance_proposal>().HasIndex(a => new { a.proposalId }).IsUnique(true);
            modelBuilder.Entity<flowInstance_result>().HasIndex(a => new { a.documentId }).IsUnique(true);
            modelBuilder.Entity<operation_log>().HasIndex(a => new { a.userId, a.operationDate }).IsUnique(true);
            modelBuilder.Entity<flowInstance_achievement>().HasIndex(a => new { a.achievementId }).IsUnique(true);
            modelBuilder.Entity<rsProject_uncollected>().HasIndex(a => new { a.companyId, a.rsProjectId, a.year }).IsUnique(true);
            modelBuilder.Entity<c_rd_funds>().HasIndex(a => new { a.companyId, a.year, a.month, a.type }).IsUnique(true);
            modelBuilder.Entity<c_rd_funds_district>().HasIndex(a => new { a.deptId, a.year, a.month, a.type }).IsUnique(true);
            modelBuilder.Entity<c_rd_summary_district>().HasIndex(a => new { a.deptId, a.year }).IsUnique(true);
            modelBuilder.Entity<patent_plan_info>().HasIndex(a => new { a.patentPlanId }).IsUnique(true);
            modelBuilder.Entity<flowInstance_rdFee>().HasIndex(a => new { a.rdFeeId }).IsUnique(true);
            modelBuilder.Entity<flowInstance_form>().HasIndex(a => new { a.moduleId,a.formId }).IsUnique(true);
            modelBuilder.Entity<service_apply_auditor>().HasIndex(a => new { a.serviceApplyId }).IsUnique(true);
            modelBuilder.Entity<flowInstance_curNode>().HasIndex(a => new { a.instanceId,a.curNodeId }).IsUnique(true);
            modelBuilder.Entity<contract_project>().HasIndex(a => new { a.customerId,a.productId,a.signCnt }).IsUnique(true);
            // policySource 设置默认值
            modelBuilder.Entity<policySource>().Property(b => b.method).HasDefaultValue("GET");
            modelBuilder.Entity<policySource>().Property(b => b.pageMax).HasDefaultValue(10);
            modelBuilder.Entity<policySource>().Property(b => b.dataParse).HasDefaultValue("RE");
            modelBuilder.Entity<policySource>().Property(b => b.dataContent).HasDefaultValue("RE");
            modelBuilder.Entity<flowNode>().Property(b => b.skip).HasDefaultValue(true);
            modelBuilder.Entity<flowInstance>().Property(b => b.revokable).HasDefaultValue(true);
        }
        public static void InitData(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.InsertData("ys_dept", new[] { "deptName", "parentId", "level", "identity", "creatorId", "createTime" }
                  , new object[] { "优赛集团", -1, 0, "", -1, DateTime.Now });

            var dUser = "ysadmin";
            var dPwd = "ysadmin!@#$";
            string hashPwd = null;
            using (var md5 = MD5.Create())
            {
                var result = md5.ComputeHash(Encoding.UTF8.GetBytes(dUser + dPwd));
                hashPwd = BitConverter.ToString(result).Replace("-", "");
            }
            migrationBuilder.InsertData("ys_user", new[] { "userName", "password", "realName", "gender", "tel", "depId", "postion", "status", "avatar", "remark", "creatorId", "createTime" }
                            , new object[] { dUser, hashPwd, "超级管理员", 1, "", 1, "负责人", 1, "", "初始用户", -1, DateTime.Now });
        }

        public DbSet<app_menu> app_menu { get; set; }
        public DbSet<app_role> app_role { get; set; }
        public DbSet<app_role_menu> app_role_menu { get; set; }
        public DbSet<app_user_role> app_user_role { get; set; }
        public DbSet<ys_dept> ys_dept { get; set; }
        public DbSet<ys_user> ys_user { get; set; }
        public DbSet<sys_session> sys_session { get; set; }
        public DbSet<customer> customer { get; set; }
        public DbSet<expert> expert { get; set; }
        public DbSet<h3_customer> h3_customer { get; set; }

        public DbSet<d_user> d_user { get; set; }
        public DbSet<user_dept> user_dept { get; set; }

        public DbSet<ys_dept_manager> ys_dept_manager { get; set; }
        public DbSet<patent> patent { get; set; }
        public DbSet<patent_apply> patent_apply { get; set; }
        public DbSet<patent_cost> patent_cost { get; set; }
        public DbSet<sys_log> sys_log { get; set; }
        public DbSet<product> product { get; set; }
        public DbSet<project> project { get; set; }
        public DbSet<project_member> project_member { get; set; }
        public DbSet<app_role_data> app_role_data { get; set; }
        public DbSet<customer_user> customer_user { get; set; }
        public DbSet<customer_account> customer_account { get; set; }
        public DbSet<patent_account> patent_account { get; set; }
        public DbSet<contractTraceability> contractTraceability { get; set; }
        public DbSet<h3_agreement> h3_agreement { get; set; }
        public DbSet<h3_project> h3_project { get; set; }
        public DbSet<policySource> policySource { get; set; }
        public DbSet<policyContent> policyContent { get; set; }
        public DbSet<serviceLog> serviceLog { get; set; }
        public DbSet<serviceUser> serviceUser { get; set; }
        public DbSet<policySourceUser> policySourceUser { get; set; }
        public DbSet<project_progress_detail> project_progress_detail { get; set; }
        public DbSet<project_summary> project_summary { get; set; }
        public DbSet<district_summary> district_summary { get; set; }
        public DbSet<patent_master> patent_master { get; set; }
        public DbSet<project_tech_log> project_tech_log { get; set; }
        public DbSet<product_stage> product_stage { get; set; }
        public DbSet<project_tech_stage> project_tech_stage { get; set; }
        public DbSet<project_register> project_register { get; set; }
        public DbSet<project_member_log> project_member_log { get; set; }
        public DbSet<patentData> patentData { get; set; }
        public DbSet<tech_summary> tech_summary { get; set; }
        public DbSet<project_basic> project_basic { get; set; }
        public DbSet<project_timeline> project_timeline { get; set; }
        public DbSet<project_tech_info> project_tech_info { get; set; }
        public DbSet<project_finance_info> project_finance_info { get; set; }
        public DbSet<project_summary_data> project_summary_data { get; set; }
        public DbSet<flowNode> flowNode { get; set; }
        public DbSet<flowNode_user> flowNode_user { get; set; }
        public DbSet<flowBranch> flowBranch { get; set; }
        public DbSet<flowInstance> flowInstance { get; set; }
        public DbSet<flowInstance_project> flowInstance_project { get; set; }
        public DbSet<flowInstance_user> flowInstance_user { get; set; }
        public DbSet<flow> flow { get; set; }
        public DbSet<flowModule> flowModule { get; set; }
        public DbSet<pushAudit> pushAudit { get; set; }
        public DbSet<flowInstance_log> flowInstance_log { get; set; }
        public DbSet<flowInstance_docFile> flowInstance_docFile { get; set; }
        public DbSet<project_audit_summary> project_audit_summary { get; set; }
        public DbSet<flowInstance_report> flowInstance_report { get; set; }
        public DbSet<flowInstance_patent> flowInstance_patent { get; set; }
        public DbSet<patent_buying_demand> patent_buying_demand { get; set; }
        public DbSet<patent_buying_list> patent_buying_list { get; set; }
        public DbSet<patent_sea> patent_sea { get; set; }
        public DbSet<solution> solution { get; set; }
        public DbSet<solution_share> solution_share { get; set; }
        public DbSet<innovation_project> innovation_project { get; set; }
        public DbSet<innovation_member> innovation_member { get; set; }
        public DbSet<rsProject_master> rsProject_master { get; set; }
        public DbSet<rsProject_summary> rsProject_summary { get; set; }
        public DbSet<daily_report> daily_report { get; set; }
        public DbSet<daily_report_detail> daily_report_detail { get; set; }
        public DbSet<monthly_report> monthly_report { get; set; }
        public DbSet<monthly_report_detail> monthly_report_detail { get; set; }
        public DbSet<patent_plan> patent_plan { get; set; }
        public DbSet<article> article { get; set; }
        public DbSet<patent_audit> patent_audit { get; set; }
        public DbSet<patent_plan_process> patent_plan_process { get; set; }
        public DbSet<flowInstance_result> flowInstance_result { get; set; }
        public DbSet<flowInstance_proposal> flowInstance_proposal { get; set; }
        public DbSet<operation_log> operation_log { get; set; }
        public DbSet<flowInstance_achievement> flowInstance_achievement { get; set; }
        public DbSet<rsProject_uncollected> rsProject_uncollected { get; set; }
        public DbSet<c_rd_funds> c_rd_funds { get; set; }
        public DbSet<c_rd_funds_district> c_rd_funds_district { get; set; }
        public DbSet<c_rd_summary_district> c_rd_summary_district { get; set; }
        public DbSet<patent_demand> patent_demand { get; set; }
        public DbSet<patent_demand_member> patent_demand_member { get; set; }
        public DbSet<patent_plan_info> patent_plan_info { get; set; }
        public DbSet<flowInstance_rdFee> flowInstance_rdFee { get; set; }
        public DbSet<quality_score> quality_score { get; set; }
        public DbSet<customer_trace> customer_trace { get; set; }
        public DbSet<quality_score_month> quality_score_month { get; set; }
        public DbSet<quality_type> quality_type { get; set; }
        public DbSet<quality_score_log> quality_score_log { get; set; }
        public DbSet<checkInst> checkInst { get; set; }
        public DbSet<checkInst_price> checkInst_price { get; set; }
        public DbSet<service_apply> service_apply { get; set; }
        public DbSet<service_apply_customer> service_apply_customer { get; set; }
        public DbSet<service_apply_member> service_apply_member { get; set; }
        public DbSet<work_record> work_record { get; set; }
        public DbSet<work_record_item> work_record_item { get; set; }
        public DbSet<flowInstance_form> flowInstance_form { get; set; }
        public DbSet<service_apply_auditor> service_apply_auditor { get; set; }
        public DbSet<flowInstance_curNode> flowInstance_curNode { get; set; }
        public DbSet<contract> contract { get; set; }
        public DbSet<contract_member> contract_member { get; set; }
        public DbSet<contract_project> contract_project { get; set; }
        public DbSet<patent_supplier> patent_supplier {get;set;}
        public DbSet<checkPayment> checkPayment { get; set; }
        public DbSet<checkPayment_rd> checkPayment_rd { get; set; }
        public DbSet<finaDaily> finaDaily { get; set; }
    }
}

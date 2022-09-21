using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class table_time : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "user",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "user",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "t_projectQuota",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "t_projectQuota",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "t_projectOther",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "t_projectOther",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "t_projectInvestMent",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "t_projectInvestMent",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "t_projectImplement",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "t_projectImplement",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "t_projectCost",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "t_projectCost",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "t_projectBasic",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "t_projectBasic",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "t_projectAppendix",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "t_projectAppendix",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "t_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "t_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "t_declaration",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "t_declaration",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "sys_tableField",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "sys_tableField",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "createTime",
                table: "sys_insuranceConfig",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "creatorId",
                table: "sys_insuranceConfig",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "sys_insuranceConfig",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "sys_insuranceConfig",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "sys_insuranceConfig",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "sys_insuranceConfig",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "sys_document",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "sys_document",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "sys_document",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "sys_document",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "rdDept",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "rdDept",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "rdAccountDetail",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "rdAccountDetail",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_worktimeRatio",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_worktimeRatio",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "p_summary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_summary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_summary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_stage",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_stage",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_report",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "p_report",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_report",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_report",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_project",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "p_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_material",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_material",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_inspection",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_inspection",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_file",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_file",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "createTime",
                table: "p_equipment_used",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "creatorId",
                table: "p_equipment_used",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_equipment_used",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "p_equipment_used",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_equipment_used",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_equipment_used",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_energy",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_energy",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_document",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_document",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_design",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_design",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "createTime",
                table: "p_attendance_used",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "creatorId",
                table: "p_attendance_used",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_attendance_used",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "p_attendance_used",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_attendance_used",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_attendance_used",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_attendance_month",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_attendance_month",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "insuranceConfig",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "insuranceConfig",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "insuranceConfig",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "i_member",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "i_member",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "i_member",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "i_member",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<DateTime>(
                name: "createTime",
                table: "i_equipment",
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "i_equipment",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "i_equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "i_equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "i_equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "employee",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "employee",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "dept",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "dept",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "dept",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "dept",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "d_salary",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "d_salary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "d_salary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "d_salary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "d_material",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "d_material",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "d_material",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "d_material",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "d_inspection",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "d_inspection",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "d_inspection",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "d_inspection",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "d_equipment",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "d_equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "d_equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "d_equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "d_energy",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "d_energy",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "d_energy",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "d_energy",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "d_design",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "d_design",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "d_design",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "d_design",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "d_bonus",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "d_bonus",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "d_bonus",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "d_bonus",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "d_attendance",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "d_attendance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "d_attendance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "d_attendance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "company",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "company",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "c_support",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "c_support",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "c_ownership",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "c_ownership",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "c_financialCondition",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "c_financialCondition",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msCreatorId",
                table: "c_annualReport",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "c_annualReport",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "user");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "user");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "t_projectQuota");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "t_projectQuota");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "t_projectOther");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "t_projectOther");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "t_projectInvestMent");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "t_projectInvestMent");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "t_projectImplement");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "t_projectImplement");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "t_projectCost");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "t_projectCost");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "t_projectBasic");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "t_projectBasic");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "t_projectAppendix");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "t_projectAppendix");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "t_project");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "t_project");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "t_declaration");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "t_declaration");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "sys_tableField");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "sys_tableField");

            migrationBuilder.DropColumn(
                name: "createTime",
                table: "sys_insuranceConfig");

            migrationBuilder.DropColumn(
                name: "creatorId",
                table: "sys_insuranceConfig");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "sys_insuranceConfig");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "sys_insuranceConfig");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "sys_insuranceConfig");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "sys_insuranceConfig");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "sys_document");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "sys_document");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "sys_document");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "sys_document");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "rdDept");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "rdDept");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "rdAccountDetail");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "rdAccountDetail");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_worktimeRatio");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_worktimeRatio");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "p_summary");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_summary");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_summary");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_stage");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_stage");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "p_report");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "p_report");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_report");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_report");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_material");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_material");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_inspection");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_inspection");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_file");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_file");

            migrationBuilder.DropColumn(
                name: "createTime",
                table: "p_equipment_used");

            migrationBuilder.DropColumn(
                name: "creatorId",
                table: "p_equipment_used");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "p_equipment_used");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "p_equipment_used");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_equipment_used");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_equipment_used");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_equipment");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_equipment");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_document");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_document");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_design");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_design");

            migrationBuilder.DropColumn(
                name: "createTime",
                table: "p_attendance_used");

            migrationBuilder.DropColumn(
                name: "creatorId",
                table: "p_attendance_used");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "p_attendance_used");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "p_attendance_used");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_attendance_used");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_attendance_used");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_attendance_month");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_attendance_month");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "insuranceConfig");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "insuranceConfig");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "insuranceConfig");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "i_member");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "i_member");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "i_member");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "i_member");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "i_equipment");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "i_equipment");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "i_equipment");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "i_equipment");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "employee");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "employee");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "dept");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "dept");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "dept");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "dept");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "d_inspection");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "d_inspection");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "d_inspection");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "d_inspection");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "d_equipment");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "d_equipment");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "d_equipment");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "d_equipment");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "d_energy");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "d_energy");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "d_energy");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "d_energy");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "d_design");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "d_design");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "d_design");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "d_design");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "d_bonus");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "d_bonus");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "d_bonus");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "d_bonus");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "d_attendance");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "d_attendance");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "d_attendance");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "d_attendance");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "company");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "company");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "c_support");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "c_support");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "c_ownership");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "c_ownership");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "c_financialCondition");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "c_financialCondition");

            migrationBuilder.DropColumn(
                name: "msCreatorId",
                table: "c_annualReport");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "c_annualReport");

            migrationBuilder.AlterColumn<int>(
                name: "createTime",
                table: "i_equipment",
                nullable: false,
                oldClrType: typeof(DateTime));
        }
    }
}

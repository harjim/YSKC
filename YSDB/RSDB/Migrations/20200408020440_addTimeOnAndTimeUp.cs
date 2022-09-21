using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addTimeOnAndTimeUp : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "usedAttData",
                table: "p_attendance_used");

            migrationBuilder.DropColumn(
                name: "workHours",
                table: "p_attendance_used");

            migrationBuilder.DropColumn(
                name: "attData",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "bonus",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "dayHours",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "endowmentOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "hasModify",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "houseOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "injuryOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "insuranceFund",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "maternityOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "medicalOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "pay",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdBonus",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdDays",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdEndowmentOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdHouseOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdInjuryOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdInsuranceFund",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdMaternityOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdMedicalOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdPay",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdUnemploymentOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "unemploymentOfCom",
                table: "p_attendance");

            migrationBuilder.RenameColumn(
                name: "month",
                table: "p_attendance_used",
                newName: "workDate");

            migrationBuilder.RenameIndex(
                name: "IX_p_attendance_used_companyId_enumber_month",
                table: "p_attendance_used",
                newName: "IX_p_attendance_used_companyId_enumber_workDate");

            migrationBuilder.RenameColumn(
                name: "month",
                table: "p_attendance",
                newName: "workDate");

            migrationBuilder.RenameColumn(
                name: "onTime",
                table: "c_attendance",
                newName: "onTime1");

            migrationBuilder.RenameColumn(
                name: "offTime",
                table: "c_attendance",
                newName: "offTime1");

            migrationBuilder.AlterColumn<decimal>(
                name: "remainHours",
                table: "p_attendance_used",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "timeRange",
                table: "p_attendance_used",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "workHour",
                table: "p_attendance_used",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "offTime1",
                table: "p_attendance",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "offTime2",
                table: "p_attendance",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "offTime3",
                table: "p_attendance",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "onTime1",
                table: "p_attendance",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "onTime2",
                table: "p_attendance",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "onTime3",
                table: "p_attendance",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "workHour",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "offTime2",
                table: "c_attendance",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "offTime3",
                table: "c_attendance",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "onTime2",
                table: "c_attendance",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "onTime3",
                table: "c_attendance",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "timeRange",
                table: "p_attendance_used");

            migrationBuilder.DropColumn(
                name: "workHour",
                table: "p_attendance_used");

            migrationBuilder.DropColumn(
                name: "offTime1",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "offTime2",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "offTime3",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "onTime1",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "onTime2",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "onTime3",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "workHour",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "offTime2",
                table: "c_attendance");

            migrationBuilder.DropColumn(
                name: "offTime3",
                table: "c_attendance");

            migrationBuilder.DropColumn(
                name: "onTime2",
                table: "c_attendance");

            migrationBuilder.DropColumn(
                name: "onTime3",
                table: "c_attendance");

            migrationBuilder.RenameColumn(
                name: "workDate",
                table: "p_attendance_used",
                newName: "month");

            migrationBuilder.RenameIndex(
                name: "IX_p_attendance_used_companyId_enumber_workDate",
                table: "p_attendance_used",
                newName: "IX_p_attendance_used_companyId_enumber_month");

            migrationBuilder.RenameColumn(
                name: "workDate",
                table: "p_attendance",
                newName: "month");

            migrationBuilder.RenameColumn(
                name: "onTime1",
                table: "c_attendance",
                newName: "onTime");

            migrationBuilder.RenameColumn(
                name: "offTime1",
                table: "c_attendance",
                newName: "offTime");

            migrationBuilder.AlterColumn<int>(
                name: "remainHours",
                table: "p_attendance_used",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AddColumn<string>(
                name: "usedAttData",
                table: "p_attendance_used",
                maxLength: 120,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<int>(
                name: "workHours",
                table: "p_attendance_used",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "attData",
                table: "p_attendance",
                maxLength: 120,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<decimal>(
                name: "bonus",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<int>(
                name: "dayHours",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<decimal>(
                name: "endowmentOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<bool>(
                name: "hasModify",
                table: "p_attendance",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<decimal>(
                name: "houseOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "injuryOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "insuranceFund",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "maternityOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "medicalOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "pay",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdBonus",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdDays",
                table: "p_attendance",
                type: "decimal(5,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdEndowmentOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdHouseOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdInjuryOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdInsuranceFund",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdMaternityOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdMedicalOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdPay",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdUnemploymentOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "unemploymentOfCom",
                table: "p_attendance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);
        }
    }
}

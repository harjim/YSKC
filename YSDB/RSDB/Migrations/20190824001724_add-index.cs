using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addindex : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "bonus",
                table: "p_attendance",
                type: "decimal(12,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdBonus",
                table: "p_attendance",
                type: "decimal(12,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AlterColumn<decimal>(
                name: "bonus",
                table: "d_bonus",
                type: "decimal(12,2)",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(10,2)");

            migrationBuilder.AddColumn<DateTime>(
                name: "endTime",
                table: "d_bonus",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<bool>(
                name: "isUsed",
                table: "d_bonus",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<DateTime>(
                name: "startTime",
                table: "d_bonus",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<decimal>(
                name: "totalBonus",
                table: "d_bonus",
                type: "decimal(12,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.CreateIndex(
                name: "IX_p_equipment_used_companyId_ecode_month",
                table: "p_equipment_used",
                columns: new[] { "companyId", "ecode", "month" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_p_attendance_used_companyId_enumber_month",
                table: "p_attendance_used",
                columns: new[] { "companyId", "enumber", "month" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_equipment_companyId_ecode",
                table: "equipment",
                columns: new[] { "companyId", "ecode" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_d_equipment_ecode_month_companyId",
                table: "d_equipment",
                columns: new[] { "ecode", "month", "companyId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_equipment_used_companyId_ecode_month",
                table: "p_equipment_used");

            migrationBuilder.DropIndex(
                name: "IX_p_attendance_used_companyId_enumber_month",
                table: "p_attendance_used");

            migrationBuilder.DropIndex(
                name: "IX_equipment_companyId_ecode",
                table: "equipment");

            migrationBuilder.DropIndex(
                name: "IX_d_equipment_ecode_month_companyId",
                table: "d_equipment");

            migrationBuilder.DropColumn(
                name: "bonus",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdBonus",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "endTime",
                table: "d_bonus");

            migrationBuilder.DropColumn(
                name: "isUsed",
                table: "d_bonus");

            migrationBuilder.DropColumn(
                name: "startTime",
                table: "d_bonus");

            migrationBuilder.DropColumn(
                name: "totalBonus",
                table: "d_bonus");

            migrationBuilder.AlterColumn<decimal>(
                name: "bonus",
                table: "d_bonus",
                type: "decimal(10,2)",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(12,2)");
        }
    }
}

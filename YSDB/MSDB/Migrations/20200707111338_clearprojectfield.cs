using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class clearprojectfield : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "description",
                table: "project");

            migrationBuilder.DropColumn(
                name: "endDate",
                table: "project");

            migrationBuilder.DropColumn(
                name: "estimateExpense",
                table: "project");

            migrationBuilder.DropColumn(
                name: "fBeginDate",
                table: "project");

            migrationBuilder.DropColumn(
                name: "fDeptId",
                table: "project");

            migrationBuilder.DropColumn(
                name: "fDistrict",
                table: "project");

            migrationBuilder.DropColumn(
                name: "fEndDate",
                table: "project");

            migrationBuilder.DropColumn(
                name: "financeStatus",
                table: "project");

            migrationBuilder.DropColumn(
                name: "status",
                table: "project");

            migrationBuilder.DropColumn(
                name: "tBeginDate",
                table: "project");

            migrationBuilder.DropColumn(
                name: "tDeptId",
                table: "project");

            migrationBuilder.DropColumn(
                name: "tDistrict",
                table: "project");

            migrationBuilder.DropColumn(
                name: "tEndDate",
                table: "project");

            migrationBuilder.DropColumn(
                name: "techStatus",
                table: "project");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "description",
                table: "project",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "endDate",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "estimateExpense",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "fBeginDate",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "fDeptId",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "fDistrict",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "fEndDate",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "financeStatus",
                table: "project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "status",
                table: "project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "tBeginDate",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "tDeptId",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "tDistrict",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "tEndDate",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "techStatus",
                table: "project",
                nullable: false,
                defaultValue: 0);
        }
    }
}

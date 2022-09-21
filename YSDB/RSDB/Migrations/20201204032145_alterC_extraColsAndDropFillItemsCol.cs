using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterC_extraColsAndDropFillItemsCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "compnyId",
                table: "c_extra");

            migrationBuilder.DropColumn(
                name: "manageAddrCode",
                table: "c_extra");

            migrationBuilder.DropColumn(
                name: "compnyId",
                table: "c_employment_info");

            migrationBuilder.DropColumn(
                name: "compnyId",
                table: "c_bank_info");

            migrationBuilder.RenameColumn(
                name: "filledItems",
                table: "c_extra",
                newName: "companyId");

            migrationBuilder.RenameColumn(
                name: "companyAddress",
                table: "c_extra",
                newName: "manageAddress");

            migrationBuilder.RenameColumn(
                name: "filledItems",
                table: "c_employment_info",
                newName: "companyId");

            migrationBuilder.RenameColumn(
                name: "filledItems",
                table: "c_bank_info",
                newName: "companyId");

            migrationBuilder.AlterColumn<DateTime>(
                name: "startTime",
                table: "c_support",
                nullable: true,
                oldClrType: typeof(DateTime));

            migrationBuilder.AlterColumn<DateTime>(
                name: "endTime",
                table: "c_support",
                nullable: true,
                oldClrType: typeof(DateTime));

            migrationBuilder.AddColumn<int>(
                name: "endYear",
                table: "c_support",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "startYear",
                table: "c_support",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "endYear",
                table: "c_support");

            migrationBuilder.DropColumn(
                name: "startYear",
                table: "c_support");

            migrationBuilder.RenameColumn(
                name: "manageAddress",
                table: "c_extra",
                newName: "companyAddress");

            migrationBuilder.RenameColumn(
                name: "companyId",
                table: "c_extra",
                newName: "filledItems");

            migrationBuilder.RenameColumn(
                name: "companyId",
                table: "c_employment_info",
                newName: "filledItems");

            migrationBuilder.RenameColumn(
                name: "companyId",
                table: "c_bank_info",
                newName: "filledItems");

            migrationBuilder.AlterColumn<DateTime>(
                name: "startTime",
                table: "c_support",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldNullable: true);

            migrationBuilder.AlterColumn<DateTime>(
                name: "endTime",
                table: "c_support",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldNullable: true);

            migrationBuilder.AddColumn<int>(
                name: "compnyId",
                table: "c_extra",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "manageAddrCode",
                table: "c_extra",
                maxLength: 30,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "compnyId",
                table: "c_employment_info",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "compnyId",
                table: "c_bank_info",
                nullable: false,
                defaultValue: 0);
        }
    }
}

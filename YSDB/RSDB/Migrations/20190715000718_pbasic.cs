using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class pbasic : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "companyId",
                table: "p_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "createTime",
                table: "p_project",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "creatorId",
                table: "p_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "deptId",
                table: "p_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "estimateExpense",
                table: "p_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "masterENumber",
                table: "p_project",
                maxLength: 30,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "tecIndustry",
                table: "p_project",
                maxLength: 20,
                nullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "email",
                table: "company",
                maxLength: 100,
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "domain",
                table: "company",
                maxLength: 100,
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "companyId",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "createTime",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "creatorId",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "deptId",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "estimateExpense",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "masterENumber",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "tecIndustry",
                table: "p_project");

            migrationBuilder.AlterColumn<string>(
                name: "email",
                table: "company",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 100,
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "domain",
                table: "company",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 100,
                oldNullable: true);
        }
    }
}

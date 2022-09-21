using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class editAudit : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "auditTime",
                table: "project_audit",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "auditor",
                table: "project_audit",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "content",
                table: "project_audit",
                maxLength: 200,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<int>(
                name: "status",
                table: "project_audit",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "type",
                table: "project_audit",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "auditTime",
                table: "project_audit");

            migrationBuilder.DropColumn(
                name: "auditor",
                table: "project_audit");

            migrationBuilder.DropColumn(
                name: "content",
                table: "project_audit");

            migrationBuilder.DropColumn(
                name: "status",
                table: "project_audit");

            migrationBuilder.DropColumn(
                name: "type",
                table: "project_audit");
        }
    }
}

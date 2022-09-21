using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterSourceUserRemoveTablebase : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "creatorId",
                table: "policySourceUser");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "policySourceUser");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "policySourceUser");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "creatorId",
                table: "policySourceUser",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "policySourceUser",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "policySourceUser",
                nullable: false,
                defaultValue: 0);
        }
    }
}

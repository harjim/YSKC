using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class dropUselessCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "creatorId",
                table: "policyContent");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "policyContent");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "policyContent");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "creatorId",
                table: "policyContent",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "policyContent",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "policyContent",
                nullable: false,
                defaultValue: 0);
        }
    }
}

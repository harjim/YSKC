using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_yield_configHoursColsAndTestsCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<TimeSpan>(
                name: "testEndTime",
                table: "p_yield_config",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "testHour",
                table: "p_yield_config",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "testStartTime",
                table: "p_yield_config",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "totalHour",
                table: "p_yield_config",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "trialHour",
                table: "p_yield_config",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "testEndTime",
                table: "p_yield_config");

            migrationBuilder.DropColumn(
                name: "testHour",
                table: "p_yield_config");

            migrationBuilder.DropColumn(
                name: "testStartTime",
                table: "p_yield_config");

            migrationBuilder.DropColumn(
                name: "totalHour",
                table: "p_yield_config");

            migrationBuilder.DropColumn(
                name: "trialHour",
                table: "p_yield_config");
        }
    }
}

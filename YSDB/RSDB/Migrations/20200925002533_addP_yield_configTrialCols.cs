using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_yield_configTrialCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<TimeSpan>(
                name: "endTime",
                table: "p_yield_config",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "planYield",
                table: "p_yield_config",
                type: "decimal(18,6)",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "startTime",
                table: "p_yield_config",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "trialDate",
                table: "p_yield_config",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "unit",
                table: "p_yield_config",
                maxLength: 10,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "endTime",
                table: "p_yield_config");

            migrationBuilder.DropColumn(
                name: "planYield",
                table: "p_yield_config");

            migrationBuilder.DropColumn(
                name: "startTime",
                table: "p_yield_config");

            migrationBuilder.DropColumn(
                name: "trialDate",
                table: "p_yield_config");

            migrationBuilder.DropColumn(
                name: "unit",
                table: "p_yield_config");
        }
    }
}

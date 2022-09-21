using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class budgetAddMonthAndStage : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "month",
                table: "p_budget",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "stage",
                table: "p_budget",
                maxLength: 10,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "month",
                table: "p_budget");

            migrationBuilder.DropColumn(
                name: "stage",
                table: "p_budget");
        }
    }
}

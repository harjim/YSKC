using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addPatent_planSubmitAndDemandDateCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "demandDate",
                table: "patent_plan",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "submitDate",
                table: "patent_plan",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "demandDate",
                table: "patent_plan");

            migrationBuilder.DropColumn(
                name: "submitDate",
                table: "patent_plan");
        }
    }
}

using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addPatent_plan_processAddRemarkCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "remark",
                table: "patent_plan_process",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "process",
                table: "patent_plan",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "remark",
                table: "patent_plan_process");

            migrationBuilder.DropColumn(
                name: "process",
                table: "patent_plan");
        }
    }
}

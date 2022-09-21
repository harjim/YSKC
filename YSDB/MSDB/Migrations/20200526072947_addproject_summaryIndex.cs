using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addproject_summaryIndex : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_project_summary_customerId_projectId_year",
                table: "project_summary",
                columns: new[] { "customerId", "projectId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_project_summary_customerId_projectId_year",
                table: "project_summary");
        }
    }
}

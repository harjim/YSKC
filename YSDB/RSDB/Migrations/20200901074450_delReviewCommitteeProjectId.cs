using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class delReviewCommitteeProjectId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "projectId",
                table: "p_reviewCommittee");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "projectId",
                table: "p_reviewCommittee",
                nullable: false,
                defaultValue: 0);
        }
    }
}

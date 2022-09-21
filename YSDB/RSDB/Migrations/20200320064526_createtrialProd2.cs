﻿using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createtrialProd2 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_trialProd",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    tBeginDate = table.Column<DateTime>(nullable: false),
                    planPO = table.Column<int>(nullable: false),
                    ActualPO = table.Column<int>(nullable: false),
                    workshopId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_trialProd", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_trialProd");
        }
    }
}
